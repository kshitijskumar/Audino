package com.example.audino.player.mediasource

import android.support.v4.media.MediaBrowserCompat
import android.util.Log
import com.example.audino.model.repositories.MainRepository
import com.example.audino.model.response.GenreResponse
import com.example.audino.player.extensionfunctions.toMediaItem
import com.example.audino.player.mediasource.State.*
import com.example.audino.utils.Constants.ROOT_ID
import com.example.audino.utils.Injector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MediaSource(
    private val mainRepository: MainRepository = Injector.provideMainRepository()
) {

    private val actionsList = mutableListOf<((Boolean) -> Unit)>()

    private var allGenres: List<GenreResponse> = listOf()

    private suspend fun getAllBooks() = withContext(Dispatchers.IO) {
        sourceState = STATE_INITIALIZING
        try {
            val apiResponse = mainRepository.getAllBooks()
            allGenres = apiResponse.genres
        } catch (e: Exception) {
            sourceState = STATE_ERROR
            Log.d("MediaSource", "error is: ${e.message}")
        }
    }

    private var sourceState: State = STATE_CREATED
        set(value) {
            if (value == STATE_INITIALIZED || value == STATE_ERROR) {
                synchronized(actionsList) {
                    actionsList.forEach { action ->
                        action(value == STATE_INITIALIZED)
                    }
                }
            }
            field = value
        }

    fun whenReady(action: (Boolean) -> Unit) : Boolean {
        return if (sourceState == STATE_CREATED || sourceState == STATE_INITIALIZING) {
            actionsList.add(action)
            false
        } else {
            action(sourceState == STATE_INITIALIZED)
            true
        }
    }

    fun getMediaItemsForId(id: String) : MutableList<MediaBrowserCompat.MediaItem> {
        val mediaItemsList = mutableListOf<MediaBrowserCompat.MediaItem>()

        when(id) {
            ROOT_ID -> {
                //if rootid then the home screen is shown
                allGenres.forEach { genre ->
                    val mediaItem = genre.toMediaItem()
                    mediaItemsList.add(mediaItem)
                }
            }
            else -> {
                //find that genre and then get all the books converted in that list
                allGenres.find { it.genreId == id }?.books?.forEach { book ->
                    val mediaItem = book.toMediaItem()
                    mediaItemsList.add(mediaItem)
                } ?: Unit
            }
        }

        return mediaItemsList
    }



}

enum class State {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR
}
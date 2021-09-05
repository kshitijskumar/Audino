package com.example.audino.viewmodels

import android.media.MediaMetadata.METADATA_KEY_MEDIA_ID
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.util.Log
import androidx.lifecycle.*
import com.example.audino.model.response.BookResponse
import com.example.audino.model.response.GenreResponse
import com.example.audino.player.extensionfunctions.toBookResponse
import com.example.audino.player.extensionfunctions.toGenreResponse
import com.example.audino.service.AudinoServiceConnection
import com.example.audino.utils.Constants.MIN_15
import com.example.audino.utils.Constants.MIN_30
import com.example.audino.utils.Constants.MIN_45
import com.example.audino.utils.Constants.NO_TIME
import com.example.audino.utils.Constants.ROOT_ID

class MainViewModel(
    private val serviceConnection: AudinoServiceConnection
) : ViewModel() {

    val isConnected = serviceConnection.isConnected
    val currBook = serviceConnection.currBook
    val playbackState = serviceConnection.playbackState

    private val _isPlaying = MutableLiveData(false)
    val isPlaying: LiveData<Boolean> get() = _isPlaying

    private val _genresList = MutableLiveData<List<GenreResponse>>()
    val genresList : LiveData<List<GenreResponse>> get() = _genresList

    private val _sleepTimerTime = MutableLiveData(SleepTimerLevel.NoTimer)
    val sleepTimerTime: LiveData<SleepTimerLevel> get() = _sleepTimerTime

    init {
        serviceConnection.subscribe(ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {
            override fun onChildrenLoaded(
                parentId: String,
                children: MutableList<MediaBrowserCompat.MediaItem>
            ) {
                super.onChildrenLoaded(parentId, children)
                val reqList = children.map {
                    it.toGenreResponse()
                }
                //this list for now contains no books just genre, will get the books too when showing this to ui
                _genresList.postValue(reqList)
            }
        })
    }

    fun playBookFromStart(book: BookResponse) {

        Log.d("PlayPauseId", "book id: ${book.bookId}")
        Log.d("PlayPauseId", "metadata id: ${currBook.value?.getText(METADATA_KEY_MEDIA_ID)}")
        if (book.bookId != currBook.value?.getText(METADATA_KEY_MEDIA_ID)) {
            val bundle = Bundle().apply {
                putSerializable("Book", book)
            }
            serviceConnection.transportControls.playFromMediaId(book.bookId, bundle)
        } else {
            Log.d("PlayPauseId", "playing is: ${isPlaying.value}")
            if (isPlaying.value == true) {
                serviceConnection.transportControls.pause()
                togglePlayState(false)
            } else {
                serviceConnection.transportControls.play()
                togglePlayState(true)
            }
        }
    }

    fun setSleepTimer() {
        Log.d("SleepTimer", "in main vm")
        _sleepTimerTime.value = when(_sleepTimerTime.value) {
            SleepTimerLevel.NoTimer -> SleepTimerLevel.Timer15
            SleepTimerLevel.Timer15 -> SleepTimerLevel.Timer30
            SleepTimerLevel.Timer30 -> SleepTimerLevel.Timer45
            SleepTimerLevel.Timer45 -> SleepTimerLevel.NoTimer
            else -> SleepTimerLevel.NoTimer
        }
        serviceConnection.setSleepTimer(_sleepTimerTime.value?.time ?: 0L)
    }

    fun setPendingSleepTimer(level: SleepTimerLevel) {
        _sleepTimerTime.value = level
    }

    fun stopCurrentlyPlayingBook() {
        serviceConnection.transportControls.stop()
        togglePlayState(false)
    }

    fun togglePlayState(isPlaying: Boolean) {
        _isPlaying.value = isPlaying
    }

    companion object {
        private class MainViewModelFactory(private val serviceConnection: AudinoServiceConnection) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(serviceConnection) as T
            }
        }

        fun getMainViewModel(storeOwner: ViewModelStoreOwner, serviceConnection: AudinoServiceConnection) : MainViewModel {
            return ViewModelProvider(storeOwner, MainViewModelFactory(serviceConnection))[MainViewModel::class.java]
        }
    }

}

enum class SleepTimerLevel(val time: Long) {
    NoTimer(NO_TIME),
    Timer15(MIN_15),
    Timer30(MIN_30),
    Timer45(MIN_45)

}
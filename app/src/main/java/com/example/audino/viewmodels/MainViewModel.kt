package com.example.audino.viewmodels

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.util.Log
import androidx.lifecycle.*
import com.example.audino.model.response.BookResponse
import com.example.audino.model.response.GenreResponse
import com.example.audino.player.extensionfunctions.toBookResponse
import com.example.audino.player.extensionfunctions.toGenreResponse
import com.example.audino.service.AudinoServiceConnection
import com.example.audino.utils.Constants.ROOT_ID

class MainViewModel(
    private val serviceConnection: AudinoServiceConnection
) : ViewModel() {

    val isConnected = serviceConnection.isConnected
    val currBook = serviceConnection.currBook
    val playbackState = serviceConnection.playbackState

    private val _genresList = MutableLiveData<List<GenreResponse>>()
    val genresList : LiveData<List<GenreResponse>> get() = _genresList

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
        val bundle = Bundle().apply {
            putSerializable("Book", book)
        }
        serviceConnection.transportControls.playFromMediaId(book.bookId, bundle)
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
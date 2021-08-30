package com.example.audino.viewmodels

import android.support.v4.media.MediaBrowserCompat
import androidx.lifecycle.*
import com.example.audino.model.response.GenreResponse
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
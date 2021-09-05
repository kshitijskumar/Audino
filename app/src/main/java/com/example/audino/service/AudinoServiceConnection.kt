package com.example.audino.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.audino.utils.Constants.ACTION_SCHEDULE_SLEEP_TIMER

class AudinoServiceConnection(
    private val context: Context
) {

    private val _isConnected = MutableLiveData<Boolean>(false)
    val isConnected: LiveData<Boolean> get() = _isConnected

    private val _playbackState = MutableLiveData<PlaybackStateCompat?>()
    val playbackState: LiveData<PlaybackStateCompat?> get() = _playbackState

    private val _currBook = MutableLiveData<MediaMetadataCompat?>()
    val currBook: LiveData<MediaMetadataCompat?> get() = _currBook

    private val connectionCallback = AudinoConnectionCallback(context)

    private val controllerCallback = object : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            _playbackState.postValue(state)
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
            _currBook.postValue(metadata)
        }
    }

    private lateinit var mediaController: MediaControllerCompat

    val transportControls: MediaControllerCompat.TransportControls
        get() = mediaController.transportControls

    private val mediaBrowser = MediaBrowserCompat(
        context,
        ComponentName(context, AudinoService::class.java),
        connectionCallback,
        null
    ).apply {
        connect()
    }

    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.subscribe(parentId, callback)
    }

    fun playFromMediaId(mediaId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        subscribe(mediaId, callback)
    }

    fun setSleepTimer(forHowLong: Long) {
        val intent = Intent(ACTION_SCHEDULE_SLEEP_TIMER).apply {
            putExtra("sleepTimer", forHowLong)
        }
        LocalBroadcastManager.getInstance(context.applicationContext)
            .sendBroadcast(intent)
    }


    private inner class AudinoConnectionCallback(private val context: Context) : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            super.onConnected()
            _isConnected.postValue(true)
            Log.d("GenresList", "onConnected")
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(controllerCallback)
            }
        }

        override fun onConnectionFailed() {
            super.onConnectionFailed()
            _isConnected.postValue(false)
        }
    }
}
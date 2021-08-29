package com.example.audino.service

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.content.ContextCompat
import androidx.media.MediaBrowserServiceCompat
import com.example.audino.player.callbacks.AudinoSessionCallback
import com.example.audino.player.notification.AudinoNotificationManager
import com.example.audino.utils.Constants.ROOT_ID
import com.example.audino.utils.Injector
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class AudioBookService : MediaBrowserServiceCompat() {

    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var stateBuilder: PlaybackStateCompat.Builder
    private lateinit var exoplayer: SimpleExoPlayer

    private var isForeground = false

    private val mediaSource by lazy {
        Injector.provideMediaSource()
    }

    private lateinit var notificationManager: AudinoNotificationManager

    override fun onCreate() {
        super.onCreate()

        exoplayer = SimpleExoPlayer.Builder(this)
            .build()

        //setup media session
        mediaSession = MediaSessionCompat(this, "AUDINO_SERVICE_TAG").apply {
            setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS or MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS)
            stateBuilder = PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE)
            setPlaybackState(stateBuilder.build())
            setSessionToken(sessionToken)
            setCallback(AudinoSessionCallback(exoplayer))
        }

        //setup notification
        notificationManager = AudinoNotificationManager(
            this,
            mediaSession.sessionToken,
            PlayerNotificationListener()
        )
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot(ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        val caseHandled = mediaSource.whenReady {
            if (it) {
                //initialized
                result.sendResult(mediaSource.getMediaItemsForId(parentId))
            } else {
                //error
                result.sendError(Bundle().apply {
                    putString("error", "Something went wrong")
                })
            }
        }
        if (!caseHandled) {
            //mediasource not initialized. detach this, sendResult is stored in actions list
            result.detach()
        }
    }

    private inner class PlayerNotificationListener: PlayerNotificationManager.NotificationListener {
        override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
            super.onNotificationCancelled(notificationId, dismissedByUser)
            stopForeground(true)
            isForeground = false
            stopSelf()
        }

        override fun onNotificationPosted(
            notificationId: Int,
            notification: Notification,
            ongoing: Boolean
        ) {
            super.onNotificationPosted(notificationId, notification, ongoing)
            if (ongoing && !isForeground) {
                ContextCompat.startForegroundService(
                    applicationContext,
                    Intent(applicationContext, this@AudioBookService::class.java)
                )
                startForeground(notificationId, notification)
                isForeground = true
            }
        }
    }
}
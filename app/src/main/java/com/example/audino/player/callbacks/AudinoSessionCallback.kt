package com.example.audino.player.callbacks

import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.core.net.toUri
import com.example.audino.model.response.BookResponse
import com.example.audino.player.extensionfunctions.toMediaItem
import com.example.audino.player.extensionfunctions.toMediaMetadataCompat
import com.example.audino.player.notification.AudinoNotificationManager
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer

class AudinoSessionCallback(
    private val player: Player,
    private val mediaSession: MediaSessionCompat,
    private val notificationManager: AudinoNotificationManager
) : MediaSessionCompat.Callback() {

    override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
        super.onPlayFromMediaId(mediaId, extras)
        val book = extras?.getSerializable("Book") as BookResponse
        Log.d("PlayBook", "Book to play: $book and id: $mediaId")
        activeSessionAndChangeMetaData(book, PlaybackStateCompat.STATE_PLAYING, 0L, 1f, true)
        player.setMediaItem(MediaItem.fromUri(book.audioUrl ?: ""))
        player.prepare()
        player.play()
        notificationManager.showNotification(player)
    }

    override fun onPlay() {
        super.onPlay()
        activeSessionAndChangeMetaData(null, PlaybackStateCompat.STATE_PLAYING, player.currentPosition, 1f, true)
        player.play()
        notificationManager.showNotification(player)
    }

    override fun onPause() {
        super.onPause()
        activeSessionAndChangeMetaData(null, PlaybackStateCompat.STATE_PAUSED, player.currentPosition, 1f, false)
        player.pause()
        notificationManager.hideNotification()
    }

    override fun onStop() {
        super.onStop()
        activeSessionAndChangeMetaData(null, PlaybackStateCompat.STATE_STOPPED, player.currentPosition, 1f, false)
        player.stop()
        notificationManager.hideNotification()
    }

    private fun activeSessionAndChangeMetaData(book: BookResponse?, state: Int, position: Long, speed: Float, isActive: Boolean) {
        mediaSession.isActive = isActive
        if (book != null) { mediaSession.setMetadata(book.toMediaMetadataCompat()) }
        mediaSession.setPlaybackState(PlaybackStateCompat.Builder().setState(state, position, speed).build())
    }

}
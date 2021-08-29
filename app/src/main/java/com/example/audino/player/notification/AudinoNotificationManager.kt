package com.example.audino.player.notification

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.audino.R
import com.example.audino.utils.Constants.CHANNEL_ID
import com.example.audino.utils.Constants.NOTIFICATION_ID
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import kotlinx.coroutines.*

class AudinoNotificationManager(
    private val context: Context,
    private val sessionToken: MediaSessionCompat.Token,
    private val notificationListener: PlayerNotificationManager.NotificationListener
) {

    private val notificationManager: PlayerNotificationManager

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)


    init {
        val mediaController = MediaControllerCompat(context, sessionToken)
        notificationManager = PlayerNotificationManager
            .Builder(
                context,
                NOTIFICATION_ID,
                CHANNEL_ID
            ).apply {
                setMediaDescriptionAdapter(DescriptionAdapter(mediaController))
            }
            .build().apply {
                setMediaSessionToken(sessionToken)
                setSmallIcon(R.drawable.ic_play)
            }

    }

    fun hideNotification() = notificationManager.setPlayer(null)

    fun showNotification(player: Player) = notificationManager.setPlayer(player)

    private inner class DescriptionAdapter(
        private val controllerCompat: MediaControllerCompat
    ) : PlayerNotificationManager.MediaDescriptionAdapter {

        var currentIconUri: Uri? = null
        var currentBitmap: Bitmap? = null

        override fun getCurrentContentTitle(player: Player): CharSequence {
            return controllerCompat.metadata.description.title.toString()
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return controllerCompat.sessionActivity
        }

        override fun getCurrentContentText(player: Player): CharSequence? {
            return controllerCompat.metadata.description.description.toString()
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            val iconUri = controllerCompat.metadata.description.iconUri
            return if (currentIconUri != iconUri || currentBitmap == null) {
                currentIconUri = iconUri
                serviceScope.launch {
                    currentBitmap = iconUri?.let {
                        resolveUriAsBitmap(it)
                    }
                    currentBitmap?.let { callback.onBitmap(it) }
                }
                null
            } else {
                currentBitmap
            }
        }

        private suspend fun resolveUriAsBitmap(uri: Uri) : Bitmap {
            return withContext(Dispatchers.IO) {
                Glide.with(context)
                    .applyDefaultRequestOptions(glideOptions)
                    .asBitmap()
                    .load(uri)
                    .submit(NOTIFICATION_LARGE_ICON_SIZE, NOTIFICATION_LARGE_ICON_SIZE)
                    .get()
            }
        }
    }
}

const val NOTIFICATION_LARGE_ICON_SIZE = 144 // px

private val glideOptions = RequestOptions()
    .fallback(R.drawable.ic_play)
    .diskCacheStrategy(DiskCacheStrategy.DATA)
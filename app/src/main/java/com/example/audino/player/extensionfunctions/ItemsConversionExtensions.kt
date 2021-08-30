package com.example.audino.player.extensionfunctions

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_BROWSABLE
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import com.example.audino.model.response.BookResponse
import com.example.audino.model.response.GenreResponse

fun GenreResponse.toMediaItem() : MediaBrowserCompat.MediaItem {
    val desc = MediaDescriptionCompat.Builder()
        .setMediaId(genreId)
        .setTitle(genreName)
        .build()
    return MediaBrowserCompat.MediaItem(desc, FLAG_BROWSABLE)
}

fun BookResponse.toMediaItem() : MediaBrowserCompat.MediaItem {
    val desc = MediaDescriptionCompat.Builder()
        .setMediaId(bookId)
        .setTitle(bookName)
        .setMediaUri(audioUrl?.toUri())
        .setSubtitle(authorName)
        .setDescription(description)
        .setExtras(bundleOf("summary" to summary))
        .setIconUri(thumbnailUrl?.toUri())
        .build()
    return MediaBrowserCompat.MediaItem(desc, FLAG_PLAYABLE)
}

fun MediaBrowserCompat.MediaItem.toGenreResponse() : GenreResponse {
    return GenreResponse(
        genreId = mediaId,
        genreName = description.title.toString()
    )
}

fun MediaBrowserCompat.MediaItem.toBookResponse() : BookResponse {
    return BookResponse(
        bookId = mediaId,
        bookName = this.description.title.toString(),
        audioUrl = description.mediaUri.toString(),
        authorName = description.subtitle.toString(),
        thumbnailUrl = description.iconUri.toString(),
        description = description.description.toString(),
        summary = description.extras?.getString("summary") ?: ""
    )
}
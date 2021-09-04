package com.example.audino.player.extensionfunctions

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_BROWSABLE
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.MediaMetadataCompat.*
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import com.example.audino.model.response.BookResponse
import com.example.audino.model.response.GenreResponse
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata

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

fun BookResponse.toMediaMetadataCompat() : MediaMetadataCompat {
    return MediaMetadataCompat.Builder()
        .putText(METADATA_KEY_TITLE, bookName)
        .putText(METADATA_KEY_ARTIST, authorName)
        .putText(METADATA_KEY_ALBUM_ART_URI, thumbnailUrl)
        .putText(METADATA_KEY_MEDIA_URI, thumbnailUrl)
        .putText(METADATA_KEY_DISPLAY_ICON_URI, thumbnailUrl)
        .putText(METADATA_KEY_MEDIA_ID, bookId)
        .build()
}

fun BookResponse.toExoplayerMediaItem() : MediaItem {
    return MediaItem.Builder()
        .setMediaId(bookId ?: "")
        .setMediaMetadata(MediaMetadata.Builder()
            .setTitle(bookName)
            .setArtist(authorName)
            .setMediaUri(audioUrl?.toUri())
            .setArtworkUri(thumbnailUrl?.toUri())
            .build())
        .build()
}
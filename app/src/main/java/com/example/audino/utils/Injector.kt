package com.example.audino.utils

import com.example.audino.model.repositories.MainRepository
import com.example.audino.model.repositories.MainRepositoryImpl
import com.example.audino.player.mediasource.MediaSource

object Injector {

    private var mainRepository: MainRepository? = null
    private var mediaSource: MediaSource? = null

    fun provideMainRepository() : MainRepository {
        if (mainRepository == null) {
            mainRepository = MainRepositoryImpl()
        }
        return mainRepository!!
    }

    fun provideMediaSource() : MediaSource {
        if (mediaSource == null) {
            mediaSource = MediaSource()
        }
        return mediaSource!!
    }
}
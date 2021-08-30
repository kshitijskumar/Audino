package com.example.audino.utils

import android.content.Context
import com.example.audino.model.repositories.MainRepository
import com.example.audino.model.repositories.MainRepositoryImpl
import com.example.audino.player.mediasource.MediaSource
import com.example.audino.service.AudinoServiceConnection

class Injector private constructor() {

    private var mainRepository: MainRepository? = null
    private var mediaSource: MediaSource? = null

    private var serviceConnection: AudinoServiceConnection? = null

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

    fun provideAudinoServiceConnection(context: Context) : AudinoServiceConnection {
        if (serviceConnection == null) {
            serviceConnection = AudinoServiceConnection(context.applicationContext)
        }

        return serviceConnection!!
    }

    companion object {
        private var injector: Injector? = null
        fun getInjector() : Injector {
            if (injector == null) {
                injector = Injector()
            }

            return injector!!
        }
    }
}
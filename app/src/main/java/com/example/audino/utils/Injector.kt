package com.example.audino.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.audino.model.api.ApiService
import com.example.audino.model.dao.SavedBooksDao
import com.example.audino.model.db.AudinoDatabase
import com.example.audino.model.repositories.MainRepository
import com.example.audino.model.repositories.MainRepositoryImpl
import com.example.audino.player.mediasource.MediaSource
import com.example.audino.service.AudinoServiceConnection
import com.example.audino.utils.Constants.SHARED_PREFS_NAME
import java.lang.IllegalStateException

class Injector private constructor() {

    private var mainRepository: MainRepository? = null
    private var mediaSource: MediaSource? = null

    private var sharedPrefs: SharedPreferences? = null

    private var serviceConnection: AudinoServiceConnection? = null

    private var apiService: ApiService? = null

    private var saveBookDao: SavedBooksDao? = null

    private var needContext: ((String) -> Context)? = null

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

    fun setupSharedPrefs(context: Context) {
        sharedPrefs = context.applicationContext.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
    }

    fun provideSharedPrefs() : SharedPreferences {
        return sharedPrefs ?: throw IllegalStateException("Preference not instantiated to provide")
    }

    fun provideApiService() : ApiService {
        if (apiService == null) {
            apiService = ApiService.getApiService()
        }
        return apiService!!
    }

    fun providesSaveBooksDao() : SavedBooksDao {
        if (saveBookDao == null) {
            saveBookDao = AudinoDatabase.getAudinoDb(needContext!!.invoke("Audino Databse setup")).savedBooksDao()
        }
        return saveBookDao!!
    }

    companion object {
        private var injector: Injector? = null
        fun getInjector() : Injector {
            if (injector == null) {
                injector = Injector()
            }

            return injector!!
        }

        fun setupInjector(needContext: (String) -> Context) {
            getInjector().apply {
                this.needContext = needContext
                this.setupSharedPrefs(needContext.invoke("Setup shared prefs"))
            }

        }
    }
}
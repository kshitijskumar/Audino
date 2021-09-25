package com.example.audino

import android.app.Application
import com.example.audino.utils.Injector

class AudinoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Injector.getInjector().setupSharedPrefs(this)
    }
}
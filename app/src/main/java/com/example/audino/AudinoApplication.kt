package com.example.audino

import android.app.Application
import android.util.Log
import com.example.audino.utils.Injector

class AudinoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Injector.setupInjector {
            Log.d("InjectorSetup", "Reason: $it")
            this
        }
    }
}
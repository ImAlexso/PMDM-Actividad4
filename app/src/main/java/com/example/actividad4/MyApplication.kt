package com.example.actividad4

import android.app.Application
import com.example.actividad4.di.AppContainer

class MyApplication : Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}

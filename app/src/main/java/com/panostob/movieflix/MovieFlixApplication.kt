package com.panostob.movieflix

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieFlixApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
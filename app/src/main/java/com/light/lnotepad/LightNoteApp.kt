package com.light.lnotepad

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LightNoteApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
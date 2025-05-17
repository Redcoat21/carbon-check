package com.carbondev.carboncheck

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CarbonCheckApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any libraries or SDKs here if needed
    }
}
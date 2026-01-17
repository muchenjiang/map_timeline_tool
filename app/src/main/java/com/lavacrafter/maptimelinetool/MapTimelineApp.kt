package com.lavacrafter.maptimelinetool

import android.app.Application
import org.osmdroid.config.Configuration

class MapTimelineApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance().userAgentValue = packageName
    }
}
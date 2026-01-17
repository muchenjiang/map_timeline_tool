package com.lavacrafter.maptimelinetool

import android.app.Application
import java.io.File
import org.osmdroid.config.Configuration

class MapTimelineApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val config = Configuration.getInstance()
        config.userAgentValue = packageName
        val basePath = File(cacheDir, "osmdroid")
        config.osmdroidBasePath = basePath
        config.osmdroidTileCache = File(basePath, "tiles")
    }
}
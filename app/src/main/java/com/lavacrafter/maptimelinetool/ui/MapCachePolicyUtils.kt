package com.lavacrafter.maptimelinetool.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import org.osmdroid.config.Configuration

private const val DEFAULT_CACHE_BYTES = 50L * 1024L * 1024L

fun applyMapCachePolicy(context: Context, policy: MapCachePolicy) {
    val config = Configuration.getInstance()
    val allowCache = when (policy) {
        MapCachePolicy.DISABLED -> false
        MapCachePolicy.ALWAYS -> true
        MapCachePolicy.WIFI_ONLY -> isOnWifi(context)
    }
    val maxBytes = if (allowCache) DEFAULT_CACHE_BYTES else 0L
    config.tileFileSystemCacheMaxBytes = maxBytes
}

private fun isOnWifi(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val caps = cm.getNetworkCapabilities(network) ?: return false
    return caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
}

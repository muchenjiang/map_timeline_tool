package com.lavacrafter.maptimelinetool.ui

import android.content.Context

object SettingsStore {
    private const val PREFS = "map_timeline_settings"
    private const val KEY_TIMEOUT = "timeout_seconds"
    private const val KEY_CACHE_POLICY = "cache_policy"

    fun getTimeoutSeconds(context: Context): Int {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getInt(KEY_TIMEOUT, 20)
            .coerceIn(5, 300)
    }

    fun setTimeoutSeconds(context: Context, seconds: Int) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_TIMEOUT, seconds.coerceIn(5, 300))
            .apply()
    }

    fun getCachePolicy(context: Context): MapCachePolicy {
        val value = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getInt(KEY_CACHE_POLICY, MapCachePolicy.ALWAYS.value)
        return MapCachePolicy.fromValue(value)
    }

    fun setCachePolicy(context: Context, policy: MapCachePolicy) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_CACHE_POLICY, policy.value)
            .apply()
    }
}

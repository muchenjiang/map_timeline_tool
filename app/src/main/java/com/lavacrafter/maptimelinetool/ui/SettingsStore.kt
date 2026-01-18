package com.lavacrafter.maptimelinetool.ui

import android.content.Context

object SettingsStore {
    private const val PREFS = "map_timeline_settings"
    private const val KEY_TIMEOUT = "timeout_seconds"
    private const val KEY_CACHE_POLICY = "cache_policy"
    private const val KEY_PINNED_TAGS = "pinned_tags"
    private const val KEY_RECENT_TAGS = "recent_tags"
    private const val KEY_ZOOM_BEHAVIOR = "zoom_behavior"
    private const val KEY_LANGUAGE_PREFERENCE = "language_preference"

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

    fun getPinnedTagIds(context: Context): List<Long> {
        return parseLongList(context, KEY_PINNED_TAGS)
    }

    fun setPinnedTagIds(context: Context, tagIds: List<Long>) {
        saveLongList(context, KEY_PINNED_TAGS, tagIds)
    }

    fun getRecentTagIds(context: Context): List<Long> {
        return parseLongList(context, KEY_RECENT_TAGS)
    }

    fun addRecentTagId(context: Context, tagId: Long): List<Long> {
        val updated = getRecentTagIds(context).toMutableList().apply {
            remove(tagId)
            add(0, tagId)
            while (size > 3) removeLast()
        }
        saveLongList(context, KEY_RECENT_TAGS, updated)
        return updated
    }

    fun getZoomButtonBehavior(context: Context): ZoomButtonBehavior {
        val value = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getInt(KEY_ZOOM_BEHAVIOR, ZoomButtonBehavior.HIDE.value)
        return ZoomButtonBehavior.fromValue(value)
    }

    fun setZoomButtonBehavior(context: Context, behavior: ZoomButtonBehavior) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_ZOOM_BEHAVIOR, behavior.value)
            .apply()
    }

    fun getLanguagePreference(context: Context): LanguagePreference {
        val value = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getInt(KEY_LANGUAGE_PREFERENCE, LanguagePreference.FOLLOW_SYSTEM.value)
        return LanguagePreference.fromValue(value)
    }

    fun setLanguagePreference(context: Context, preference: LanguagePreference) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_LANGUAGE_PREFERENCE, preference.value)
            .apply()
    }

    private fun parseLongList(context: Context, key: String): List<Long> {
        val raw = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(key, null)
            ?: return emptyList()
        return raw.split(',').mapNotNull { it.trim().toLongOrNull() }
    }

    private fun saveLongList(context: Context, key: String, values: List<Long>) {
        val data = values.joinToString(",")
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(key, data)
            .apply()
    }
}

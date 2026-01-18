package com.lavacrafter.maptimelinetool.ui

enum class MapCachePolicy(val value: Int) {
    DISABLED(0),
    WIFI_ONLY(1),
    ALWAYS(2);

    companion object {
        fun fromValue(value: Int): MapCachePolicy = values().firstOrNull { it.value == value } ?: ALWAYS
    }
}

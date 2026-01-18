package com.lavacrafter.maptimelinetool.ui

enum class LanguagePreference(val value: Int, val localeTag: String?) {
    FOLLOW_SYSTEM(0, null),
    ENGLISH(1, "en"),
    CHINESE(2, "zh");

    companion object {
        fun fromValue(value: Int): LanguagePreference {
            return values().firstOrNull { it.value == value } ?: FOLLOW_SYSTEM
        }
    }
}

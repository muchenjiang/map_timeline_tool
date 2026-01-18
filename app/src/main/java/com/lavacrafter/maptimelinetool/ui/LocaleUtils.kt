package com.lavacrafter.maptimelinetool.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

fun applyLanguagePreference(preference: LanguagePreference) {
    val locales = when (preference) {
        LanguagePreference.FOLLOW_SYSTEM -> LocaleListCompat.getEmptyLocaleList()
        else -> LocaleListCompat.forLanguageTags(preference.localeTag.orEmpty())
    }
    AppCompatDelegate.setApplicationLocales(locales)
}

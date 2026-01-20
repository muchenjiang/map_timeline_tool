package com.lavacrafter.maptimelinetool.ui

sealed interface SettingsRoute {
    object Main : SettingsRoute
    object MapOperations : SettingsRoute
    object Cache : SettingsRoute
    object Download : SettingsRoute
    object DefaultTags : SettingsRoute
}

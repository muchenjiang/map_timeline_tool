package com.lavacrafter.maptimelinetool.ui

enum class ZoomButtonBehavior(val value: Int) {
    HIDE(0),
    WHEN_ACTIVE(1),
    ALWAYS(2);

    companion object {
        fun fromValue(value: Int): ZoomButtonBehavior = values().firstOrNull { it.value == value } ?: HIDE
    }
}

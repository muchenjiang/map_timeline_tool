package com.lavacrafter.maptimelinetool.ui

data class DownloadedArea(
    val north: Double,
    val south: Double,
    val east: Double,
    val west: Double,
    val minZoom: Int,
    val maxZoom: Int,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun boundsKey(): String {
        fun round(value: Double): String = String.format("%.5f", value)
        return listOf(
            round(north),
            round(south),
            round(east),
            round(west),
            minZoom.toString(),
            maxZoom.toString()
        ).joinToString("|")
    }
}

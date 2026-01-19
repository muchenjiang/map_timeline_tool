package com.lavacrafter.maptimelinetool.ui

import android.content.Context
import com.lavacrafter.maptimelinetool.R
import org.osmdroid.tileprovider.tilesource.TileSourcePolicy

data class DownloadTileSource(
    val id: String,
    val labelRes: Int,
    val attributionRes: Int,
    val templateUrl: String,
    val policyFlags: Int
) {
    fun toOsmdroidSource(context: Context): org.osmdroid.tileprovider.tilesource.XYTileSource {
        return org.osmdroid.tileprovider.tilesource.XYTileSource(
            context.getString(labelRes),
            0,
            19,
            256,
            ".png",
            arrayOf(templateUrl),
            context.getString(attributionRes),
            TileSourcePolicy(
                4,
                policyFlags
            )
        )
    }
}

val downloadTileSources = listOf(
    DownloadTileSource(
        id = "mapnik",
        labelRes = R.string.tile_source_mapnik,
        attributionRes = R.string.tile_source_attribution_openstreetmap,
        templateUrl = "https://tile.openstreetmap.org/",
        policyFlags = TileSourcePolicy.FLAG_USER_AGENT_MEANINGFUL or TileSourcePolicy.FLAG_USER_AGENT_NORMALIZED
    ),
    DownloadTileSource(
        id = "wikimedia",
        labelRes = R.string.tile_source_wikimedia,
        attributionRes = R.string.tile_source_attribution_wikimedia,
        templateUrl = "https://maps.wikimedia.org/osm-intl/",
        policyFlags = TileSourcePolicy.FLAG_USER_AGENT_MEANINGFUL or TileSourcePolicy.FLAG_USER_AGENT_NORMALIZED
    )
)

fun downloadTileSourceById(id: String): DownloadTileSource {
    return downloadTileSources.firstOrNull { it.id == id } ?: downloadTileSources.first()
}
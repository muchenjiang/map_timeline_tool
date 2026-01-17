package com.lavacrafter.maptimelinetool.ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.lavacrafter.maptimelinetool.data.PointEntity
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@SuppressLint("MissingPermission")
@Composable
fun MapScreen(points: List<PointEntity>) {
    AndroidView(
        factory = { context ->
            MapView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setTileSource(TileSourceFactory.MAPNIK)
                controller.setZoom(16.0)
                if (points.isNotEmpty()) {
                    val last = points.first()
                    controller.setCenter(GeoPoint(last.latitude, last.longitude))
                }
            }
        },
        update = { map ->
            map.overlays.clear()
            points.forEach { p ->
                val marker = Marker(map).apply {
                    position = GeoPoint(p.latitude, p.longitude)
                    title = p.note
                    subDescription = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(java.util.Date(p.timestamp))
                }
                map.overlays.add(marker)
            }
            map.invalidate()
        }
    )
}
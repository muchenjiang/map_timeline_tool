package com.lavacrafter.maptimelinetool.ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.lavacrafter.maptimelinetool.R
import com.lavacrafter.maptimelinetool.data.PointEntity
import org.osmdroid.tileprovider.cachemanager.CacheManager
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@SuppressLint("MissingPermission")
@Composable
fun MapScreen(points: List<PointEntity>) {
    val context = LocalContext.current
    val sdf = remember {
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }
    var mapView: MapView? by remember { mutableStateOf(null) }
    var isDownloading by remember { mutableStateOf(false) }

    Box {
        AndroidView(
            factory = { viewContext ->
                MapView(viewContext).apply {
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
                    mapView = this
                }
            },
            update = { map ->
                map.overlays.clear()
                points.forEach { p ->
                    val marker = Marker(map).apply {
                        position = GeoPoint(p.latitude, p.longitude)
                        title = p.note
                        subDescription = context.getString(
                            R.string.label_utc_time,
                            sdf.format(Date(p.timestamp))
                        )
                    }
                    map.overlays.add(marker)
                }
                map.invalidate()
            }
        )

        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            text = { Text(stringResource(R.string.action_download_map)) },
            onClick = {
                val map = mapView
                if (map == null || isDownloading) return@ExtendedFloatingActionButton
                val bbox = map.boundingBox
                val currentZoom = map.zoomLevel.toInt()
                val minZoom = (currentZoom - 2).coerceAtLeast(3)
                val maxZoom = (currentZoom + 2).coerceAtMost(20)
                isDownloading = true
                Toast.makeText(context, context.getString(R.string.toast_download_start), Toast.LENGTH_SHORT).show()
                CacheManager(map).downloadAreaAsync(
                    context,
                    bbox,
                    minZoom,
                    maxZoom,
                    object : CacheManager.CacheManagerCallback {
                        override fun onTaskComplete() {
                            map.post {
                                isDownloading = false
                                Toast.makeText(context, context.getString(R.string.toast_download_complete), Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onTaskFailed(errors: Int) {
                            map.post {
                                isDownloading = false
                                Toast.makeText(context, context.getString(R.string.toast_download_failed), Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onTaskCancelled() {
                            map.post {
                                isDownloading = false
                            }
                        }

                        override fun updateProgress(progress: Int, currentZoomLevel: Int, zoomMin: Int, zoomMax: Int) {
                            // no-op
                        }
                    }
                )
            }
        )
    }
}
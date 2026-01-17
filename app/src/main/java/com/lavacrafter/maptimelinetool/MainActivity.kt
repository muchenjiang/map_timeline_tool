package com.lavacrafter.maptimelinetool

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.FileProvider
import com.lavacrafter.maptimelinetool.export.GpxExporter
import com.lavacrafter.maptimelinetool.ui.AppViewModel
import com.lavacrafter.maptimelinetool.ui.ListScreen
import com.lavacrafter.maptimelinetool.ui.MapScreen
import com.lavacrafter.maptimelinetool.ui.theme.MapTimelineToolTheme
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MapTimelineToolTheme {
                val permissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { }

                LaunchedEffect(Unit) {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }

                var tab by remember { mutableStateOf(0) }
                var showDialog by remember { mutableStateOf(false) }
                val scope = rememberCoroutineScope()
                val pointsState = viewModel.points.collectAsState().value

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Map Timeline Tool") },
                            actions = {
                                TextButton(onClick = { showDialog = true }) { Text("打点") }
                                TextButton(onClick = {
                                    scope.launch {
                                        val points = viewModel.getAllPoints()
                                        if (points.isEmpty()) {
                                            Toast.makeText(this@MainActivity, "没有数据可导出", Toast.LENGTH_SHORT).show()
                                            return@launch
                                        }
                                        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                                        val fileName = "map_timeline_${'$'}{sdf.format(Date())}.gpx"
                                        val file = File(getExternalFilesDir(null), fileName)
                                        GpxExporter.exportToFile(points, file)

                                        val uri: Uri = FileProvider.getUriForFile(
                                            this@MainActivity,
                                            "${'$'}packageName.fileprovider",
                                            file
                                        )
                                        val intent = Intent(Intent.ACTION_SEND).apply {
                                            type = "application/gpx+xml"
                                            putExtra(Intent.EXTRA_STREAM, uri)
                                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        }
                                        startActivity(Intent.createChooser(intent, "导出 GPX"))
                                    }
                                }) { Text("导出") }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = tab == 0,
                                onClick = { tab = 0 },
                                label = { Text("地图") },
                                icon = { }
                            )
                            NavigationBarItem(
                                selected = tab == 1,
                                onClick = { tab = 1 },
                                label = { Text("列表") },
                                icon = { }
                            )
                        }
                    }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        when (tab) {
                            0 -> MapScreen(points = pointsState)
                            1 -> ListScreen(points = pointsState)
                        }
                    }
                }

                if (showDialog) {
                    AddPointDialog(
                        onDismiss = { showDialog = false },
                        onConfirm = { note ->
                            val loc = viewModel.getLastKnownLocation()
                            if (loc == null) {
                                Toast.makeText(this@MainActivity, "无法获取定位，请确认权限/定位服务", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.addPoint(note, loc)
                            }
                            showDialog = false
                        }
                    )
                }
            }
        }
    }
}
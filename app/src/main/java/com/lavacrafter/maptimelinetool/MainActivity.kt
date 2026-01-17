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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.lavacrafter.maptimelinetool.export.GpxExporter
import com.lavacrafter.maptimelinetool.ui.AddPointDialog
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
                val context = LocalContext.current
                val permissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { result ->
                    val granted = result.values.all { it }
                    if (!granted) {
                        Toast.makeText(context, context.getString(R.string.toast_permission_denied), Toast.LENGTH_SHORT).show()
                    }
                }

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
                            title = { Text(stringResource(R.string.app_name)) },
                            actions = {
                                TextButton(onClick = { showDialog = true }) { Text(stringResource(R.string.action_add_point)) }
                                TextButton(onClick = {
                                    scope.launch {
                                        val points = viewModel.getAllPoints()
                                        if (points.isEmpty()) {
                                            Toast.makeText(context, context.getString(R.string.toast_no_points), Toast.LENGTH_SHORT).show()
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
                                        startActivity(Intent.createChooser(intent, context.getString(R.string.export_chooser_title)))
                                    }
                                }) { Text(stringResource(R.string.action_export_gpx)) }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = tab == 0,
                                onClick = { tab = 0 },
                                label = { Text(stringResource(R.string.tab_map)) },
                                icon = { }
                            )
                            NavigationBarItem(
                                selected = tab == 1,
                                onClick = { tab = 1 },
                                label = { Text(stringResource(R.string.tab_list)) },
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
                                Toast.makeText(context, context.getString(R.string.toast_location_failed), Toast.LENGTH_SHORT).show()
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
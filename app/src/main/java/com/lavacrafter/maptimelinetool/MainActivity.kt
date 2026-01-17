package com.lavacrafter.maptimelinetool

import android.Manifest
import android.content.Intent
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.lavacrafter.maptimelinetool.export.CsvExporter
import com.lavacrafter.maptimelinetool.ui.AboutScreen
import com.lavacrafter.maptimelinetool.ui.AddPointDialog
import com.lavacrafter.maptimelinetool.ui.AppViewModel
import com.lavacrafter.maptimelinetool.ui.ListScreen
import com.lavacrafter.maptimelinetool.ui.MapScreen
import com.lavacrafter.maptimelinetool.ui.SettingsScreen
import com.lavacrafter.maptimelinetool.ui.theme.MapTimelineToolTheme
import com.lavacrafter.maptimelinetool.notification.QuickAddService
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import org.osmdroid.config.Configuration

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            MapTimelineToolTheme(darkTheme = isDarkTheme) {
                val context = LocalContext.current
                var pendingCsv by remember { mutableStateOf<String?>(null) }
                val exportLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.CreateDocument("text/csv")
                ) { uri ->
                    val csv = pendingCsv
                    if (uri == null || csv == null) return@rememberLauncherForActivityResult
                    runCatching {
                        context.contentResolver.openOutputStream(uri)?.use { output ->
                            output.write(csv.toByteArray(Charsets.UTF_8))
                        }
                    }.onFailure {
                        Toast.makeText(context, context.getString(R.string.toast_export_failed), Toast.LENGTH_SHORT).show()
                    }
                    pendingCsv = null
                }
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
                    }
                    startService(Intent(context, QuickAddService::class.java))
                }

                var tab by remember { mutableStateOf(1) }
                var showAbout by remember { mutableStateOf(false) }
                var showDialog by remember { mutableStateOf(false) }
                var pendingTimestamp by remember { mutableStateOf<Long?>(null) }
                var selectedPointId by remember { mutableStateOf<Long?>(null) }
                val scope = rememberCoroutineScope()
                val pointsState = viewModel.points.collectAsState().value
                val exportCsv: () -> Unit = {
                    scope.launch {
                        val points = viewModel.getAllPoints()
                        if (points.isEmpty()) {
                            Toast.makeText(context, context.getString(R.string.toast_no_points), Toast.LENGTH_SHORT).show()
                            return@launch
                        }
                        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                        val fileName = "map_timeline_${'$'}{sdf.format(Date())}.csv"
                        pendingCsv = CsvExporter.buildCsv(points)
                        exportLauncher.launch(fileName)
                    }
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            actions = { }
                        )
                    },
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            modifier = Modifier.height(64.dp),
                            onClick = {
                                pendingTimestamp = System.currentTimeMillis()
                                showDialog = true
                            }
                        ) {
                            Text(stringResource(R.string.action_add_point))
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center,
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
                            NavigationBarItem(
                                selected = tab == 2,
                                onClick = { tab = 2 },
                                label = { Text(stringResource(R.string.tab_settings)) },
                                icon = { }
                            )
                        }
                    }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        when (tab) {
                            0 -> MapScreen(
                                points = pointsState,
                                selectedPointId = selectedPointId,
                                onUpdatePoint = { point, title, note -> viewModel.updatePoint(point, title, note) },
                                onDeletePoint = { point -> viewModel.deletePoint(point) },
                                isActive = tab == 0
                            )
                            1 -> ListScreen(points = pointsState) { point ->
                                selectedPointId = point.id
                                tab = 0
                            }
                            2 -> if (showAbout) {
                                AboutScreen(onBack = { showAbout = false })
                            } else {
                                SettingsScreen(
                                    isDarkTheme = isDarkTheme,
                                    onDarkThemeChange = { isDarkTheme = it },
                                    onExportCsv = exportCsv,
                                    onClearCache = {
                                        val cacheDir = Configuration.getInstance().osmdroidTileCache
                                        runCatching { cacheDir?.deleteRecursively() }
                                        Toast.makeText(context, context.getString(R.string.toast_cache_cleared), Toast.LENGTH_SHORT).show()
                                    },
                                    onOpenAbout = { showAbout = true }
                                )
                            }
                        }
                    }
                }

                if (showDialog && pendingTimestamp != null) {
                    AddPointDialog(
                        onDismiss = {
                            showDialog = false
                            pendingTimestamp = null
                        },
                        createdAt = pendingTimestamp!!,
                        onConfirm = { title, note, createdAt ->
                            val loc = viewModel.getLastKnownLocation()
                            if (loc == null) {
                                Toast.makeText(context, context.getString(R.string.toast_location_failed), Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.addPoint(title, note, loc, createdAt)
                                vibrateOnce(context)
                                Toast.makeText(context, context.getString(R.string.toast_point_added), Toast.LENGTH_SHORT).show()
                            }
                            showDialog = false
                            pendingTimestamp = null
                        }
                    )
                }
            }
        }
    }
}

private fun vibrateOnce(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(50)
    }
}
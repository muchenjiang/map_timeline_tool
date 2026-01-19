package com.lavacrafter.maptimelinetool.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lavacrafter.maptimelinetool.R
import com.lavacrafter.maptimelinetool.NetworkStatus

@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    followSystemTheme: Boolean,
    onFollowSystemThemeChange: (Boolean) -> Unit,
    timeoutSeconds: Int,
    onTimeoutSecondsChange: (Int) -> Unit,
    cachePolicy: MapCachePolicy,
    onCachePolicyChange: (MapCachePolicy) -> Unit,
    networkStatus: NetworkStatus,
    zoomBehavior: ZoomButtonBehavior,
    onZoomBehaviorChange: (ZoomButtonBehavior) -> Unit,
    markerScale: Float,
    onMarkerScaleChange: (Float) -> Unit,
    onOpenDefaultTags: () -> Unit,
    onOpenMapDownload: () -> Unit,
    downloadedAreas: List<DownloadedArea>,
    onRemoveDownloadedArea: (DownloadedArea) -> Unit,
    onDeduplicateDownloadedAreas: () -> Unit,
    onExportCsv: () -> Unit,
    onImportCsv: () -> Unit,
    onClearCache: () -> Unit,
    onOpenAbout: () -> Unit
) {
    var timeoutText by remember(timeoutSeconds) { mutableStateOf(timeoutSeconds.toString()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.settings_title))
        Spacer(modifier = Modifier.height(12.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))

        Column {
            Text(text = stringResource(R.string.settings_theme_label))
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = followSystemTheme,
                    onCheckedChange = onFollowSystemThemeChange
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.settings_theme_follow_system))
            }
            if (!followSystemTheme) {
                Spacer(modifier = Modifier.height(8.dp))
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = onDarkThemeChange
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = stringResource(R.string.settings_timeout_label))
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = timeoutText,
            onValueChange = { value ->
                timeoutText = value.filter { it.isDigit() }
                timeoutText.toIntOrNull()?.let { seconds ->
                    onTimeoutSecondsChange(seconds)
                }
            },
            label = { Text(stringResource(R.string.settings_timeout_hint)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = stringResource(R.string.settings_cache_policy_label))
        Spacer(modifier = Modifier.height(8.dp))
        val networkLabel = when (networkStatus) {
            NetworkStatus.WIFI -> stringResource(R.string.settings_network_status_wifi)
            NetworkStatus.CELLULAR -> stringResource(R.string.settings_network_status_cellular)
            NetworkStatus.NONE -> stringResource(R.string.settings_network_status_offline)
        }
        Text(text = stringResource(R.string.settings_network_status_label, networkLabel))
        Spacer(modifier = Modifier.height(4.dp))
        CachePolicyOption(
            label = stringResource(R.string.settings_cache_policy_disabled),
            selected = cachePolicy == MapCachePolicy.DISABLED,
            onSelect = { onCachePolicyChange(MapCachePolicy.DISABLED) }
        )
        CachePolicyOption(
            label = stringResource(R.string.settings_cache_policy_wifi),
            selected = cachePolicy == MapCachePolicy.WIFI_ONLY,
            onSelect = { onCachePolicyChange(MapCachePolicy.WIFI_ONLY) }
        )
        CachePolicyOption(
            label = stringResource(R.string.settings_cache_policy_always),
            selected = cachePolicy == MapCachePolicy.ALWAYS,
            onSelect = { onCachePolicyChange(MapCachePolicy.ALWAYS) }
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = stringResource(R.string.settings_zoom_behavior_label))
        Spacer(modifier = Modifier.height(8.dp))
        ZoomBehaviorOption(
            label = stringResource(R.string.settings_zoom_behavior_hide),
            selected = zoomBehavior == ZoomButtonBehavior.HIDE,
            onSelect = { onZoomBehaviorChange(ZoomButtonBehavior.HIDE) }
        )
        ZoomBehaviorOption(
            label = stringResource(R.string.settings_zoom_behavior_interaction),
            selected = zoomBehavior == ZoomButtonBehavior.WHEN_ACTIVE,
            onSelect = { onZoomBehaviorChange(ZoomButtonBehavior.WHEN_ACTIVE) }
        )
        ZoomBehaviorOption(
            label = stringResource(R.string.settings_zoom_behavior_always),
            selected = zoomBehavior == ZoomButtonBehavior.ALWAYS,
            onSelect = { onZoomBehaviorChange(ZoomButtonBehavior.ALWAYS) }
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = stringResource(R.string.settings_marker_size_label))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(R.string.settings_marker_size_value, (markerScale * 100).toInt()))
        Slider(
            value = markerScale,
            onValueChange = onMarkerScaleChange,
            valueRange = 0.3f..1.75f
        )

        Spacer(modifier = Modifier.height(24.dp))
        ListItem(
            modifier = Modifier.clickable { onOpenDefaultTags() },
            headlineContent = { Text(stringResource(R.string.settings_default_tags_title)) },
            supportingContent = { Text(stringResource(R.string.settings_default_tags_desc)) }
        )

        Spacer(modifier = Modifier.height(12.dp))
        ListItem(
            modifier = Modifier.clickable { onOpenMapDownload() },
            headlineContent = { Text(stringResource(R.string.settings_map_download_title)) },
            supportingContent = { Text(stringResource(R.string.settings_map_download_desc)) }
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(text = stringResource(R.string.settings_downloaded_areas_title))
        Spacer(modifier = Modifier.height(8.dp))
        if (downloadedAreas.isEmpty()) {
            Text(text = stringResource(R.string.settings_downloaded_areas_empty))
        } else {
            downloadedAreas.forEach { area ->
                ListItem(
                    headlineContent = { Text(stringResource(R.string.settings_downloaded_area_range, area.minZoom, area.maxZoom)) },
                    supportingContent = {
                        Text(
                            stringResource(
                                R.string.settings_downloaded_area_bounds,
                                area.north,
                                area.south,
                                area.east,
                                area.west
                            )
                        )
                    },
                    trailingContent = {
                        IconButton(onClick = { onRemoveDownloadedArea(area) }) {
                            Text(stringResource(R.string.action_delete))
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = onOpenMapDownload) {
                Text(text = stringResource(R.string.action_add))
            }
            OutlinedButton(onClick = onDeduplicateDownloadedAreas) {
                Text(text = stringResource(R.string.action_deduplicate))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onExportCsv) {
                Text(text = stringResource(R.string.action_export_csv))
            }
            Button(onClick = onImportCsv) {
                Text(text = stringResource(R.string.action_import_csv))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(onClick = onClearCache) {
            Text(text = stringResource(R.string.action_clear_cache))
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onOpenAbout
        ) {
            Text(text = stringResource(R.string.settings_about))
        }

    }
}

@Composable
private fun CachePolicyOption(label: String, selected: Boolean, onSelect: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            RadioButton(selected = selected, onClick = onSelect)
            Text(text = label, modifier = Modifier.padding(top = 12.dp))
        }
    }
}

@Composable
private fun ZoomBehaviorOption(label: String, selected: Boolean, onSelect: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            RadioButton(selected = selected, onClick = onSelect)
            Text(text = label, modifier = Modifier.padding(top = 12.dp))
        }
    }
}


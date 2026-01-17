package com.lavacrafter.maptimelinetool.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lavacrafter.maptimelinetool.R

@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    onExportCsv: () -> Unit,
    onClearCache: () -> Unit,
    onOpenAbout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.settings_title))
        Spacer(modifier = Modifier.height(12.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))

        Column {
            Text(text = stringResource(R.string.settings_theme_label))
            Spacer(modifier = Modifier.height(8.dp))
            Switch(
                checked = isDarkTheme,
                onCheckedChange = onDarkThemeChange
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onExportCsv) {
            Text(text = stringResource(R.string.action_export_csv))
        }

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(onClick = onClearCache) {
            Text(text = stringResource(R.string.action_clear_cache))
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = stringResource(R.string.settings_project_label))
        Text(text = stringResource(R.string.settings_project_url))

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.settings_open_source_title))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(R.string.open_source_androidx))
        Text(text = stringResource(R.string.open_source_compose))
        Text(text = stringResource(R.string.open_source_material))
        Text(text = stringResource(R.string.open_source_room))
        Text(text = stringResource(R.string.open_source_osmdroid))
        Text(text = stringResource(R.string.open_source_kotlin))
        Text(text = stringResource(R.string.open_source_osm))

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onOpenAbout
        ) {
            Text(text = stringResource(R.string.settings_about))
        }

    }
}

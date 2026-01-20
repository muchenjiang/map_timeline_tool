package com.lavacrafter.maptimelinetool.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lavacrafter.maptimelinetool.R

@Composable
fun AboutScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text(text = stringResource(R.string.action_back))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(R.string.about_title))
        Spacer(modifier = Modifier.height(12.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))

        Text(text = stringResource(R.string.about_project_label))
        Text(text = stringResource(R.string.settings_project_url))

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.about_open_source_title))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(R.string.open_source_androidx_license))
        Text(text = stringResource(R.string.open_source_compose_license))
        Text(text = stringResource(R.string.open_source_material_license))
        Text(text = stringResource(R.string.open_source_material_icons_license))
        Text(text = stringResource(R.string.open_source_room_license))
        Text(text = stringResource(R.string.open_source_osmdroid_license))
        Text(text = stringResource(R.string.open_source_kotlin_license))
        Text(text = stringResource(R.string.open_source_osm_license))

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.about_ai_title))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(R.string.about_ai_text))
    }
}

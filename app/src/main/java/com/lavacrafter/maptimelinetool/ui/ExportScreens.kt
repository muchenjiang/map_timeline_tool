package com.lavacrafter.maptimelinetool.ui

import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lavacrafter.maptimelinetool.R
import com.lavacrafter.maptimelinetool.data.PointEntity

sealed class ExportKind {
    object All : ExportKind()
    data class ByTag(val tagId: Long) : ExportKind()
    data class ByTime(val fromMs: Long, val toMs: Long) : ExportKind()
    data class Manual(val ids: List<Long>) : ExportKind()
}

data class ExportSelection(val includeTags: Boolean, val kind: ExportKind)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportScreens(
    points: List<PointEntity>,
    tags: List<com.lavacrafter.maptimelinetool.data.TagEntity>,
    onSelectExport: (ExportSelection) -> Unit,
    onBack: () -> Unit
) {
    var route by remember { mutableStateOf(0) }
    var includeTags by remember { mutableStateOf(true) }
        @OptIn(ExperimentalMaterial3Api::class)
        when (route) {
        0 -> ExportOptionsScreen(includeTags = includeTags, onIncludeTagsChange = { includeTags = it }, onNext = { route = 1 }, onBack = onBack)
        1 -> SubsetMenuScreen(
            onChooseByTag = { route = 2 },
            onChooseByTime = { route = 3 },
            onChooseManual = { route = 4 },
            onChooseAll = { onSelectExport(ExportSelection(includeTags, ExportKind.All)) },
            onBack = { route = 0 }
        )
        2 -> TagPickerScreen(tags = tags, onSelectTag = { tagId -> onSelectExport(ExportSelection(includeTags, ExportKind.ByTag(tagId))) }, onBack = { route = 1 })
        3 -> TimeRangePickerScreen(onExport = { fromMs, toMs -> onSelectExport(ExportSelection(includeTags, ExportKind.ByTime(fromMs, toMs))) }, onBack = { route = 1 })
        4 -> ManualSelectScreen(points = points, onExport = { ids -> onSelectExport(ExportSelection(includeTags, ExportKind.Manual(ids))) }, onBack = { route = 1 })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExportOptionsScreen(includeTags: Boolean, onIncludeTagsChange: (Boolean) -> Unit, onNext: () -> Unit, onBack: () -> Unit) {
    BackHandler { onBack() }
    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.export_chooser_title)) }) }) { padding ->
        Column(modifier = Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = includeTags, onCheckedChange = onIncludeTagsChange)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = stringResource(R.string.export_option_points_and_tags))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = true, onClick = {})
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = stringResource(R.string.export_option_points_only))
            }
            Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.action_next)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubsetMenuScreen(onChooseByTag: () -> Unit, onChooseByTime: () -> Unit, onChooseManual: () -> Unit, onChooseAll: () -> Unit, onBack: () -> Unit) {
    BackHandler { onBack() }
    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.export_subset_title)) }) }) { padding ->
        Column(modifier = Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onChooseByTag, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.export_by_tag)) }
            Button(onClick = onChooseByTime, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.export_by_time)) }
            Button(onClick = onChooseManual, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.export_manual_select)) }
            Button(onClick = onChooseAll, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.export_all)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TagPickerScreen(tags: List<com.lavacrafter.maptimelinetool.data.TagEntity>, onSelectTag: (Long) -> Unit, onBack: () -> Unit) {
    var selectedTag by remember { mutableStateOf<com.lavacrafter.maptimelinetool.data.TagEntity?>(null) }
    BackHandler { onBack() }
    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.export_by_tag)) }) }) { padding ->
        Column(modifier = Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            tags.forEach { tag ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = selectedTag == tag, onClick = { selectedTag = tag })
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = tag.name)
                }
            }
            Button(onClick = { if (selectedTag != null) onSelectTag(selectedTag!!.id) }, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.action_export_csv)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimeRangePickerScreen(onExport: (Long, Long) -> Unit, onBack: () -> Unit) {
    var from by remember { mutableStateOf(0L) }
    var to by remember { mutableStateOf(Long.MAX_VALUE) }
    BackHandler { onBack() }
    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.export_by_time)) }) }) { padding ->
        Column(modifier = Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Simple numeric entry is a placeholder; could be improved to date pickers
            Text(text = stringResource(R.string.export_time_hint))
            Button(onClick = { onExport(from, to) }, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.action_export_csv)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ManualSelectScreen(points: List<PointEntity>, onExport: (List<Long>) -> Unit, onBack: () -> Unit) {
    var selectedIds by remember { mutableStateOf(setOf<Long>()) }
    BackHandler { onBack() }
    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.export_manual_select)) }) }) { padding ->
        Column(modifier = Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            points.forEach { p ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = selectedIds.contains(p.id), onCheckedChange = { checked ->
                        selectedIds = if (checked) selectedIds + p.id else selectedIds - p.id
                    })
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = p.title)
                }
            }
            Button(onClick = { if (selectedIds.isNotEmpty()) onExport(selectedIds.toList()) }, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.action_export_csv)) }
        }
    }
}

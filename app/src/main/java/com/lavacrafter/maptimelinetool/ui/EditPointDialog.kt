package com.lavacrafter.maptimelinetool.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lavacrafter.maptimelinetool.R
import com.lavacrafter.maptimelinetool.data.PointEntity
import com.lavacrafter.maptimelinetool.data.TagEntity

@Composable
fun EditPointDialog(
    point: PointEntity,
    quickTags: List<TagEntity>,
    tags: List<TagEntity>,
    selectedTagIds: Set<Long>,
    onToggleTag: (Long) -> Unit,
    onOpenTagPicker: () -> Unit,
    onSave: (String, String) -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember(point) { mutableStateOf(point.title) }
    var note by remember(point) { mutableStateOf(point.note) }

    val selectedTags = remember(tags, selectedTagIds) {
        tags.filter { selectedTagIds.contains(it.id) }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onSave(title, note) }) {
                Text(stringResource(R.string.action_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.action_cancel)) }
        },
        title = { Text(stringResource(R.string.dialog_title_edit_point)) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.dialog_title_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text(stringResource(R.string.dialog_note_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = stringResource(R.string.label_quick_tags), fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                QuickTagGrid(
                    quickTags = quickTags,
                    selectedTagIds = selectedTagIds,
                    onTagClick = { onToggleTag(it.id) },
                    onMoreClick = onOpenTagPicker,
                    modifier = Modifier.fillMaxWidth()
                )
                if (selectedTags.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(bottom = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        selectedTags.forEach { tag ->
                            OutlinedButton(onClick = { onToggleTag(tag.id) }) {
                                Text(tag.name)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(R.string.label_lat_lon, point.latitude, point.longitude))
                TextButton(onClick = onDelete) { Text(stringResource(R.string.action_delete)) }
            }
        }
    )
}

package com.lavacrafter.maptimelinetool.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.unit.dp
import com.lavacrafter.maptimelinetool.R
import com.lavacrafter.maptimelinetool.data.PointEntity
import com.lavacrafter.maptimelinetool.data.TagEntity

@Composable
fun EditPointDialog(
    point: PointEntity,
    tags: List<TagEntity>,
    selectedTagIds: Set<Long>,
    onToggleTag: (Long, Boolean) -> Unit,
    onCreateTag: (String, (Long) -> Unit) -> Unit,
    onSave: (String, String) -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember(point) { mutableStateOf(point.title) }
    var note by remember(point) { mutableStateOf(point.note) }
    var newTagName by remember { mutableStateOf("") }

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
                Text(text = stringResource(R.string.label_tags))
                tags.forEach { tag ->
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Checkbox(
                            checked = selectedTagIds.contains(tag.id),
                            onCheckedChange = { checked -> onToggleTag(tag.id, checked) }
                        )
                        Text(text = tag.name, modifier = Modifier.weight(1f).padding(top = 12.dp))
                    }
                }
                Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                    OutlinedTextField(
                        value = newTagName,
                        onValueChange = { newTagName = it },
                        label = { Text(stringResource(R.string.label_new_tag)) },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        val name = newTagName.trim()
                        if (name.isNotEmpty()) {
                            onCreateTag(name) { tagId ->
                                onToggleTag(tagId, true)
                            }
                            newTagName = ""
                        }
                    }) {
                        Text(stringResource(R.string.action_add))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(R.string.label_lat_lon, point.latitude, point.longitude))
                TextButton(onClick = onDelete) { Text(stringResource(R.string.action_delete)) }
            }
        }
    )
}

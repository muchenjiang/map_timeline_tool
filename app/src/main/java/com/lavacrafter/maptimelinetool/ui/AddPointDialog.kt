package com.lavacrafter.maptimelinetool.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lavacrafter.maptimelinetool.R
import com.lavacrafter.maptimelinetool.data.TagEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed interface QuickTagSlot

object MoreTagSlot : QuickTagSlot

object EmptyTagSlot : QuickTagSlot

data class TagSlot(val tag: TagEntity) : QuickTagSlot

@Composable
fun AddPointDialog(
    createdAt: Long,
    quickTags: List<TagEntity>,
    tags: List<TagEntity>,
    selectedTagIds: Set<Long>,
    onToggleTag: (Long) -> Unit,
    onOpenTagPicker: () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (String, String, Long, Set<Long>) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    val defaultTitle = remember(createdAt) {
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(createdAt))
    }

    val selectedTags = remember(tags, selectedTagIds) {
        tags.filter { selectedTagIds.contains(it.id) }
    }

    val quickSlots = remember(quickTags) {
        buildList {
            quickTags.take(3).forEach { add(TagSlot(it)) }
            while (size < 3) add(EmptyTagSlot)
            add(MoreTagSlot)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(if (title.isBlank()) defaultTitle else title, note, createdAt, selectedTagIds) }) {
                Text(stringResource(R.string.action_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.action_cancel)) }
        },
        title = { Text(stringResource(R.string.dialog_title_new_point)) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.dialog_title_label)) },
                    placeholder = { Text(defaultTitle) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text(stringResource(R.string.dialog_note_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = stringResource(R.string.label_quick_tags), fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(2) { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            QuickTagCell(
                                slot = quickSlots[row * 2],
                                selectedTagIds = selectedTagIds,
                                onTagClick = { slot -> (slot as? TagSlot)?.let { onToggleTag(it.tag.id) } },
                                onMoreClick = onOpenTagPicker,
                                modifier = Modifier.weight(1f)
                            )
                            QuickTagCell(
                                slot = quickSlots[row * 2 + 1],
                                selectedTagIds = selectedTagIds,
                                onTagClick = { slot -> (slot as? TagSlot)?.let { onToggleTag(it.tag.id) } },
                                onMoreClick = onOpenTagPicker,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
                if (selectedTags.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = stringResource(R.string.label_selected_tags))
                    Spacer(modifier = Modifier.height(4.dp))
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
            }
        }
    )
}

@Composable
private fun QuickTagCell(
    slot: QuickTagSlot,
    selectedTagIds: Set<Long>,
    onTagClick: (QuickTagSlot) -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = MaterialTheme.colorScheme.outline
    val content = when (slot) {
        is TagSlot -> slot.tag.name
        MoreTagSlot -> stringResource(R.string.label_more_tags)
        EmptyTagSlot -> ""
    }
    val isSelected = slot is TagSlot && selectedTagIds.contains(slot.tag.id)
    val backgroundColor = when (slot) {
        is TagSlot -> if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable {
                when (slot) {
                    is TagSlot -> onTagClick(slot)
                    MoreTagSlot -> onMoreClick()
                    else -> Unit
                }
            }
            .padding(8.dp)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = content,
            textAlign = TextAlign.Center,
            color = when {
                slot is TagSlot && isSelected -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

@Composable
fun TagSelectionDialog(
    tags: List<TagEntity>,
    selectedTagIds: Set<Long>,
    onToggleTag: (Long) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) { Text(stringResource(R.string.action_done)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.action_cancel)) }
        },
        title = { Text(stringResource(R.string.label_tags)) },
        text = {
            Column(modifier = Modifier.height(320.dp)) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(tags) { tag ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onToggleTag(tag.id) }
                                .padding(vertical = 4.dp, horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedTagIds.contains(tag.id),
                                onCheckedChange = { onToggleTag(tag.id) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(tag.name)
                        }
                        Divider()
                    }
                }
            }
        }
    )
}
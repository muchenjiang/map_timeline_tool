package com.lavacrafter.maptimelinetool.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lavacrafter.maptimelinetool.R
import com.lavacrafter.maptimelinetool.decodePointPhoto

@Composable
fun PointPhotoSection(
    attachedPhotoPath: String?,
    pendingPhotoPath: String?,
    onCapturePhoto: () -> Unit,
    onRemovePhoto: () -> Unit,
    onConfirmPendingPhoto: () -> Unit,
    onRetakePendingPhoto: () -> Unit,
    onCancelPendingPhoto: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.label_photo),
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))

        when {
            pendingPhotoPath != null -> {
                PointPhotoPreview(photoPath = pendingPhotoPath)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.label_photo_review),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = onConfirmPendingPhoto,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.action_use_photo))
                    }
                    OutlinedButton(
                        onClick = onRetakePendingPhoto,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.action_retake_photo))
                    }
                    TextButton(
                        onClick = onCancelPendingPhoto,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.action_cancel))
                    }
                }
            }

            attachedPhotoPath != null -> {
                PointPhotoPreview(photoPath = attachedPhotoPath)
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = onCapturePhoto,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.action_retake_photo))
                    }
                    TextButton(
                        onClick = onRemovePhoto,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.action_remove_photo))
                    }
                }
            }

            else -> {
                OutlinedButton(onClick = onCapturePhoto) {
                    Text(stringResource(R.string.action_add_photo))
                }
            }
        }
    }
}

@Composable
private fun PointPhotoPreview(
    photoPath: String,
    modifier: Modifier = Modifier
) {
    val bitmap = remember(photoPath) { decodePointPhoto(photoPath) }
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 1.dp
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = stringResource(R.string.label_photo),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(R.string.label_photo_unavailable),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

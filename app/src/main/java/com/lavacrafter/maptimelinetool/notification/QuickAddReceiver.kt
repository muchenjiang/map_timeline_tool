/*
Copyright 2026 Muchen Jiang (lava-crafter)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.lavacrafter.maptimelinetool.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lavacrafter.maptimelinetool.R
import com.lavacrafter.maptimelinetool.appGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val QUICK_ADD_LOCATION_TIMEOUT_MS = 5_000L
private const val QUICK_ADD_RESULT_CHANNEL_ID = "quick_add_result_channel_v2"

class QuickAddReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val graph = context.appGraph()
                val location = graph.locationProvider.getBestEffortLocation(QUICK_ADD_LOCATION_TIMEOUT_MS)
                    ?: run {
                        showToast(context, context.getString(R.string.toast_location_failed))
                        return@launch
                    }

                val eventTime = System.currentTimeMillis()
                val timestamp = location.fixTimeMs
                    ?.takeIf { it > 0L }
                    ?.let { maxOf(eventTime, it) }
                    ?: eventTime
                val title = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(timestamp))
                graph.pointWriteUseCase.addPointWithTags(
                    title = title,
                    note = "",
                    location = location,
                    timestamp = timestamp,
                    tagIds = graph.settingsManagementUseCase.getDefaultTagIds().toSet()
                )
                showToast(context, context.getString(R.string.toast_point_added))
                vibrateOnce(context)
                showAddNotification(context)
            } finally {
                pendingResult.finish()
            }
        }
    }
}

private fun showToast(context: Context, message: String) {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

private fun showAddNotification(context: Context) {
    val channelId = QUICK_ADD_RESULT_CHANNEL_ID
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = android.app.NotificationChannel(
            channelId,
            context.getString(R.string.notification_channel_name),
            android.app.NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = context.getString(R.string.notification_channel_desc)
        }
        val manager = context.getSystemService(android.app.NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(context.getString(R.string.notification_title))
        .setContentText(context.getString(R.string.toast_point_added))
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setAutoCancel(true)
        .setOnlyAlertOnce(true)
        .setSilent(true)
        .setTimeoutAfter(2000L)
        .build()

    NotificationManagerCompat.from(context).notify(2002, notification)
}

private fun vibrateOnce(context: Context) {
    val vibrator = context.getSystemService(Vibrator::class.java) ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(50)
    }
}

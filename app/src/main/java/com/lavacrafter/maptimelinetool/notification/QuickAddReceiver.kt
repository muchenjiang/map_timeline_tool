package com.lavacrafter.maptimelinetool.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lavacrafter.maptimelinetool.R
import com.lavacrafter.maptimelinetool.data.AppDatabase
import com.lavacrafter.maptimelinetool.data.PointEntity
import com.lavacrafter.maptimelinetool.ui.HeadingLocationOverlay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class QuickAddReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        val lm = context.getSystemService(LocationManager::class.java)
        val providers = lm.getProviders(true)
        val location = providers
            .mapNotNull { lm.getLastKnownLocation(it) }
            .maxByOrNull { it.time }
            ?: readCachedLocation(context)
            ?: run {
                showToast(context, context.getString(R.string.toast_location_failed))
                pendingResult.finish()
                return
            }

        val timestamp = System.currentTimeMillis()
        val title = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(timestamp))

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val dao = AppDatabase.get(context).pointDao()
                dao.insert(
                    PointEntity(
                        timestamp = timestamp,
                        latitude = location.latitude,
                        longitude = location.longitude,
                        title = title,
                        note = ""
                    )
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

private fun readCachedLocation(context: Context) =
    context.getSharedPreferences(HeadingLocationOverlay.LOCATION_PREFS, Context.MODE_PRIVATE)
        .let { prefs ->
            if (!prefs.contains(HeadingLocationOverlay.KEY_LAT) || !prefs.contains(HeadingLocationOverlay.KEY_LON)) {
                null
            } else {
                android.location.Location("cached").apply {
                    latitude = prefs.getFloat(HeadingLocationOverlay.KEY_LAT, 0f).toDouble()
                    longitude = prefs.getFloat(HeadingLocationOverlay.KEY_LON, 0f).toDouble()
                    time = prefs.getLong(HeadingLocationOverlay.KEY_TIME, System.currentTimeMillis())
                }
            }
        }

private fun showToast(context: Context, message: String) {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

private fun showAddNotification(context: Context) {
    val channelId = "quick_add_result_channel"
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = android.app.NotificationChannel(
            channelId,
            context.getString(R.string.notification_channel_name),
            android.app.NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = context.getString(R.string.notification_channel_desc)
        }
        val manager = context.getSystemService(android.app.NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(context.getString(R.string.toast_point_added))
        .setContentText(context.getString(R.string.toast_point_added))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .setTimeoutAfter(2000L)
        .build()

    NotificationManagerCompat.from(context).notify(2002, notification)
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
package com.lavacrafter.maptimelinetool.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.lavacrafter.maptimelinetool.data.PointEntity
import com.lavacrafter.maptimelinetool.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun ListScreen(points: List<PointEntity>) {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(points) { p ->
            val timeText = stringResource(R.string.label_utc_time, sdf.format(Date(p.timestamp)))
            val latLonText = stringResource(R.string.label_lat_lon, p.latitude, p.longitude)
            ListItem(
                headlineContent = { Text(p.note) },
                supportingContent = {
                    Text("${timeText}\n${latLonText}")
                }
            )
            Divider()
        }
    }
}

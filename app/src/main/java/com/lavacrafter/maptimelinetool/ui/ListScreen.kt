package com.lavacrafter.maptimelinetool.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import com.lavacrafter.maptimelinetool.data.PointEntity
import com.lavacrafter.maptimelinetool.R

@Composable
fun ListScreen(points: List<PointEntity>, onSelect: (PointEntity) -> Unit) {
    val todayOrderById = remember(points) { buildTodayOrder(points) }
    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(points) { p ->
            val latLonText = stringResource(R.string.label_lat_lon, p.latitude, p.longitude)
            val noteText = p.note
            val orderText = todayOrderById[p.id]?.toString()
            ListItem(
                modifier = Modifier.clickable { onSelect(p) },
                headlineContent = { Text(p.title) },
                supportingContent = {
                    val detail = if (noteText.isBlank()) {
                        latLonText
                    } else {
                        "${noteText}\n${latLonText}"
                    }
                    Text(detail)
                },
                trailingContent = {
                    if (!orderText.isNullOrBlank()) {
                        Text(
                            text = orderText,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
            Divider()
        }
    }
}


package com.lavacrafter.maptimelinetool.ui

import com.lavacrafter.maptimelinetool.data.PointEntity
import java.util.Calendar

fun buildTodayOrder(points: List<PointEntity>): Map<Long, Int> {
    val calendar = Calendar.getInstance()
    val todayYear = calendar.get(Calendar.YEAR)
    val todayDay = calendar.get(Calendar.DAY_OF_YEAR)
    val todayPoints = points.filter { p ->
        calendar.timeInMillis = p.timestamp
        calendar.get(Calendar.YEAR) == todayYear && calendar.get(Calendar.DAY_OF_YEAR) == todayDay
    }.sortedBy { it.timestamp }
    return buildMap {
        todayPoints.forEachIndexed { index, point ->
            put(point.id, index + 1)
        }
    }
}

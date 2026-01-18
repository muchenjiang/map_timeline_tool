package com.lavacrafter.maptimelinetool.data

import androidx.room.Entity

@Entity(
    tableName = "point_tags",
    primaryKeys = ["pointId", "tagId"]
)
data class PointTagCrossRef(
    val pointId: Long,
    val tagId: Long
)

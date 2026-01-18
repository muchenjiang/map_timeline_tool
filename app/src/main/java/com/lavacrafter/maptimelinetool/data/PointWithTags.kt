package com.lavacrafter.maptimelinetool.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class PointWithTags(
    @Embedded val point: PointEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = PointTagCrossRef::class,
            parentColumn = "pointId",
            entityColumn = "tagId"
        )
    )
    val tags: List<TagEntity>
)

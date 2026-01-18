package com.lavacrafter.maptimelinetool.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class TagWithPoints(
    @Embedded val tag: TagEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = PointTagCrossRef::class,
            parentColumn = "tagId",
            entityColumn = "pointId"
        )
    )
    val points: List<PointEntity>
)

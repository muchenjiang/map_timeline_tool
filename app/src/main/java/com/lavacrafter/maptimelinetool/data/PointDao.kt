package com.lavacrafter.maptimelinetool.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PointDao {
    @Insert
    suspend fun insert(point: PointEntity)

    @Update
    suspend fun update(point: PointEntity)

    @Delete
    suspend fun delete(point: PointEntity)

    @Query("SELECT * FROM points ORDER BY timestamp DESC")
    fun observeAll(): Flow<List<PointEntity>>

    @Query("SELECT * FROM points ORDER BY timestamp ASC")
    suspend fun getAll(): List<PointEntity>
}
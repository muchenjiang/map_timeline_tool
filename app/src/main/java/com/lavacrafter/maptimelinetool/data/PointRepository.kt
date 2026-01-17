package com.lavacrafter.maptimelinetool.data

class PointRepository(private val dao: PointDao) {
    fun observeAll() = dao.observeAll()
    suspend fun insert(point: PointEntity) = dao.insert(point)
    suspend fun getAll() = dao.getAll()
}
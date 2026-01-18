package com.lavacrafter.maptimelinetool.data

class PointRepository(private val dao: PointDao) {
    fun observeAll() = dao.observeAll()
    suspend fun insert(point: PointEntity) = dao.insert(point)
    suspend fun update(point: PointEntity) = dao.update(point)
    suspend fun delete(point: PointEntity) = dao.delete(point)
    suspend fun getAll() = dao.getAll()

    fun observeTags() = dao.observeTags()
    suspend fun insertTag(tag: TagEntity) = dao.insertTag(tag)
    suspend fun updateTag(tag: TagEntity) = dao.updateTag(tag)
    suspend fun deleteTag(tagId: Long) = dao.deleteTag(tagId)
    suspend fun insertPointTag(pointId: Long, tagId: Long) = dao.insertPointTag(PointTagCrossRef(pointId, tagId))
    suspend fun deletePointTag(pointId: Long, tagId: Long) = dao.deletePointTag(pointId, tagId)
    suspend fun getTagIdsForPoint(pointId: Long) = dao.getTagIdsForPoint(pointId)
    fun observeTagWithPoints(tagId: Long) = dao.observeTagWithPoints(tagId)
    fun observePointsForTag(tagId: Long) = dao.observePointsForTag(tagId)
}
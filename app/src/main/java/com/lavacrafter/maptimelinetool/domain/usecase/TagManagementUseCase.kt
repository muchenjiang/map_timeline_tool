package com.lavacrafter.maptimelinetool.domain.usecase

import com.lavacrafter.maptimelinetool.data.TagEntity
import com.lavacrafter.maptimelinetool.domain.repository.PointRepositoryGateway
import kotlinx.coroutines.flow.Flow

class TagManagementUseCase(
    private val repository: PointRepositoryGateway
) {
    fun observeTags(): Flow<List<TagEntity>> = repository.observeTags()

    suspend fun addTag(name: String): Long = repository.insertTag(TagEntity(name = name))

    suspend fun renameTag(tag: TagEntity, name: String) = repository.updateTag(tag.copy(name = name))

    suspend fun deleteTag(tagId: Long) = repository.deleteTag(tagId)

    suspend fun setTagForPoint(pointId: Long, tagId: Long, enabled: Boolean) {
        if (enabled) {
            repository.insertPointTag(pointId, tagId)
        } else {
            repository.deletePointTag(pointId, tagId)
        }
    }

    suspend fun getTagIdsForPoint(pointId: Long): List<Long> = repository.getTagIdsForPoint(pointId)

    fun observePointsForTag(tagId: Long) = repository.observePointsForTag(tagId)
}

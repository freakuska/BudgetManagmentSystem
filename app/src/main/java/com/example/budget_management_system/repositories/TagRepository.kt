package com.example.budget_management_system.repositories

import com.example.budget_management_system.models.api.ApiService
import com.example.budget_management_system.models.database.TagDao
import com.example.budget_management_system.models.database.TagEntity
import com.example.budget_management_system.models.dto.TagDto
import kotlinx.coroutines.flow.Flow
import java.util.*

class TagRepository(
    private val apiService: ApiService,
    private val tagDao: TagDao
) {
    fun getAllTags(): Flow<List<TagEntity>> {
        return tagDao.getAllTags()
    }

    suspend fun getTagById(id: UUID): TagEntity? {
        return tagDao.getTagById(id)
    }

    suspend fun fetchTagsFromServer() {
        try {
            val response = apiService.getTags()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach { dto ->
                    val entity = TagEntity(
                        id = dto.id,
                        name = dto.name,
                        type = dto.type,
                        color = dto.color,
                        usageCount = dto.usageCount
                    )
                    tagDao.insertTag(entity)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun createTag(tag: TagDto) {
        try {
            val response = apiService.createTag(tag)
            if (response.isSuccessful && response.body() != null) {
                val entity = TagEntity(
                    id = response.body()!!.id,
                    name = response.body()!!.name,
                    type = response.body()!!.type,
                    color = response.body()!!.color,
                    usageCount = response.body()!!.usageCount
                )
                tagDao.insertTag(entity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteTag(id: UUID) {
        try {
            val response = apiService.deleteTag(id)
            if (response.isSuccessful) {
                tagDao.deleteTagById(id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

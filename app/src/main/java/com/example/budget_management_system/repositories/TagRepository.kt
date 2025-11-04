package com.example.budget_management_system.repositories

import com.example.budget_management_system.models.api.ApiService
import com.example.budget_management_system.models.dto.TagDto
import com.example.budget_management_system.models.dto.TagRequest
import com.example.budget_management_system.models.dto.TagResponse
import retrofit2.Response

class TagRepository(private val apiService: ApiService) {

    suspend fun getTags(): Result<List<TagResponse>> {
        return try {
            val response = apiService.getTags()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTagsTree(): Result<Response<List<TagDto>>> {
        return try {
            val response = apiService.getTagsTree()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTag(id: String): Result<Response<TagDto>> {
        return try {
            val response = apiService.getTag(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createTag(name: String, type: String, color: String? = null, icon: String? = null): Result<Response<TagDto>> {
        return try {
            val request = TagRequest(name, type, color, icon)
            val response = apiService.createTag(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateTag(id: String, name: String, type: String, color: String? = null, icon: String? = null): Result<Response<TagDto>> {
        return try {
            val request = TagRequest(name, type, color, icon)
            val response = apiService.updateTag(id, request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteTag(id: String): Result<Unit> {
        return try {
            apiService.deleteTag(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchTags(query: String): Result<Response<List<TagDto>>> {
        return try {
            val response = apiService.searchTags(query)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

package com.example.budget_management_system.models.api

import com.example.budget_management_system.models.dto.*
import com.example.budget_management_system.enums.TagType
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface ApiService {

    // ============== AUTH ==============

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequestDto): Response<AuthResponseDto>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequestDto): Response<AuthResponseDto>

    @POST("api/auth/logout")
    suspend fun logout(): Response<Unit>

    @POST("api/auth/refresh")
    suspend fun refreshToken(): Response<AuthResponseDto>

    @GET("api/auth/me")
    suspend fun getCurrentUser(): Response<UserDto>

    // ============== OPERATIONS ==============

    @GET("api/operations")
    suspend fun getOperations(
        @Query("skip") skip: Int = 0,
        @Query("take") take: Int = 50,
        @Query("type") type: OperationType? = null,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): Response<PaginatedResponseDto<FinancialOperationDto>>

    @GET("api/operations/{id}")
    suspend fun getOperation(@Path("id") id: UUID): Response<FinancialOperationDto>

    @POST("api/operations")
    suspend fun createOperation(@Body request: CreateOperationDto): Response<FinancialOperationDto>

    @PUT("api/operations/{id}")
    suspend fun updateOperation(
        @Path("id") id: UUID,
        @Body request: UpdateOperationDto
    ): Response<FinancialOperationDto>

    @DELETE("api/operations/{id}")
    suspend fun deleteOperation(@Path("id") id: UUID): Response<Unit>

    @POST("api/operations/{id}/restore")
    suspend fun restoreOperation(@Path("id") id: UUID): Response<Unit>

    @GET("api/operations/stats")
    suspend fun getStats(
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): Response<StatsDto>

    // ============== TAGS ==============

    @GET("api/tags/{id}")
    suspend fun getTag(@Path("id") id: String): Response<TagDto>

    @GET("api/tags/by-slug/{slug}")
    suspend fun getTagBySlug(@Path("slug") slug: String): Response<TagDto>

    @GET("api/tags/by-type/{type}")
    suspend fun getTagsByType(
        @Path("type") type: TagType,
        @Query("userId") userId: UUID? = null
    ): Response<List<TagDto>>

    @GET("api/tags/tree")
    suspend fun getTagsTree(
        @Query("type") type: TagType? = null,
        @Query("userId") userId: UUID? = null
    ): Response<List<TagDto>>

    @GET("api/tags/popular")
    suspend fun getPopularTags(
        @Query("count") count: Int = 10
    ): Response<List<TagDto>>

    @GET("api/tags/search")
    suspend fun searchTags(
        @Query("query") query: String,
        @Query("userId") userId: UUID? = null
    ): Response<List<TagDto>>

    @POST("api/tags")
    suspend fun createTag(@Body request: TagRequest): Response<TagDto>

    @PUT("api/tags/{id}")
    suspend fun updateTag(
        @Path("id") id: String,
        @Body request: TagRequest
    ): Response<TagDto>

    @DELETE("api/tags/{id}")
    suspend fun deleteTag(@Path("id") id: String): Response<Unit>

    @GET("api/tags")
    suspend fun getAllTags(
        @Query("type") type: TagType? = null,
        @Query("visibility") visibility: String? = null
    ): Response<List<TagDto>>

    // ============== ROLES ==============

    @GET("api/roles")
    suspend fun getAllRoles(): Response<List<RoleDto>>

    @GET("api/roles/{id}")
    suspend fun getRole(@Path("id") id: UUID): Response<RoleDto>

    @GET("api/roles/by-code/{code}")
    suspend fun getRoleByCode(@Path("code") code: String): Response<RoleDto>

    @POST("api/roles")
    suspend fun createRole(@Body request: CreateRoleDto): Response<RoleDto>

    @PUT("api/roles/{id}")
    suspend fun updateRole(
        @Path("id") id: UUID,
        @Body request: UpdateRoleDto
    ): Response<RoleDto>

    @DELETE("api/roles/{id}")
    suspend fun deleteRole(@Path("id") id: UUID): Response<Unit>

    @GET("api/roles/users/{userId}/permissions/{permission}")
    suspend fun hasPermission(
        @Path("userId") userId: UUID,
        @Path("permission") permission: String
    ): Response<Map<String, Any>>

    @GET("api/roles/users/{userId}/permissions")
    suspend fun getUserPermissions(
        @Path("userId") userId: UUID
    ): Response<Map<String, Any>>
}

enum class OperationType {
    Income,
    Expense,
    Transfer
}

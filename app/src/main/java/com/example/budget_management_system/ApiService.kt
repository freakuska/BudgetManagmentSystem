package com.example.budget_management_system

import retrofit2.Call
import retrofit2.http.*
import com.example.budget_management_system.models.dto.LoginRequestDto
import com.example.budget_management_system.models.AuthResponse
import com.example.budget_management_system.models.dto.RegisterRequestDto
import com.example.budget_management_system.models.User
import com.example.budget_management_system.models.FinancialOperation
import com.example.budget_management_system.models.dto.CreateOperationDto
import com.example.budget_management_system.models.dto.UpdateOperationDto
import com.example.budget_management_system.models.Tag
import com.example.budget_management_system.models.dto.CreateTagDto
import com.example.budget_management_system.models.dto.UpdateTagDto

interface ApiService {

    // Аутентификация
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequestDto): AuthResponse

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequestDto): AuthResponse

    @POST("/api/auth/refresh")
    suspend fun refresh(): AuthResponse

    @POST("/api/auth/logout")
    suspend fun logout()

    @GET("/api/auth/me")
    suspend fun getMe(): User

    // Операции
    @GET("/api/operations")
    suspend fun getOperations(@QueryMap filter: Map<String, String>): List<FinancialOperation>

    @POST("/api/operations")
    suspend fun createOperation(@Body dto: CreateOperationDto): FinancialOperation

    @PUT("/api/operations/{id}")
    suspend fun updateOperation(@Path("id") id: String, @Body dto: UpdateOperationDto): FinancialOperation

    @DELETE("/api/operations/{id}")
    suspend fun deleteOperation(@Path("id") id: String)

    // Теги
    @GET("/api/tags")
    suspend fun getTags(@Query("type") type: String?): List<Tag>

    @POST("/api/tags")
    suspend fun createTag(@Body dto: CreateTagDto): Tag

    @PUT("/api/tags/{id}")
    suspend fun updateTag(@Path("id") id: String, @Body dto: UpdateTagDto): Tag

    @DELETE("/api/tags/{id}")
    suspend fun deleteTag(@Path("id") id: String)
}
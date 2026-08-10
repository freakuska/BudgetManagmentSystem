package com.example.budget_management_system.repositories

import com.example.budget_management_system.models.api.ApiService
import com.example.budget_management_system.models.dto.LoginRequestDto
import com.example.budget_management_system.models.dto.RegisterRequestDto
import com.example.budget_management_system.models.security.TokenManager
import kotlinx.coroutines.flow.Flow

class AuthRepository(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun login(email: String, password: String): Result<String> = runCatching {
        val response = apiService.login(LoginRequestDto(email, password))
        val body = response.body()
        if (!response.isSuccessful || body == null) {
            throw IllegalStateException("Login failed: ${response.message()}")
        }

        tokenManager.saveTokens(body.accessToken, body.refreshToken)
        tokenManager.saveUserData(body.id.toString(), body.email)
        "Login successful"
    }

    suspend fun register(
        login: String,
        email: String,
        password: String,
        fullName: String? = null
    ): Result<String> = runCatching {
        val response = apiService.register(RegisterRequestDto(login, email, password, fullName))
        val body = response.body()
        if (!response.isSuccessful || body == null) {
            throw IllegalStateException("Registration failed: ${response.message()}")
        }

        tokenManager.saveTokens(body.accessToken, body.refreshToken)
        tokenManager.saveUserData(body.id.toString(), body.email)
        "Registration successful"
    }

    suspend fun logout(): Result<String> = runCatching {
        apiService.logout()
        tokenManager.clearTokens()
        "Logout successful"
    }

    suspend fun getCurrentUser(): Result<String> = runCatching {
        val response = apiService.getCurrentUser()
        if (!response.isSuccessful) throw IllegalStateException("Failed to get user")
        response.body()?.email ?: "Unknown"
    }

    fun getUserEmail(): Flow<String?> = tokenManager.getUserEmailFlow()
}

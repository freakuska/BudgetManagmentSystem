package com.example.budget_management_system.repositories

import com.example.budget_management_system.models.api.ApiService
import com.example.budget_management_system.models.dto.LoginRequestDto
import com.example.budget_management_system.models.dto.RegisterRequestDto
import com.example.budget_management_system.utils.TokenManager

class AuthRepository(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val request = LoginRequestDto(email, password)
            val response = apiService.login(request)

            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                tokenManager.saveTokens(
                    authResponse.accessToken,
                    authResponse.refreshToken,
                    authResponse.expiresAt
                )
                tokenManager.saveUserData(authResponse.id, authResponse.email, authResponse.login)
                Result.success("Login successful")
            } else {
                Result.failure(Exception("Login failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(
        login: String,
        email: String,
        password: String,
        fullName: String? = null
    ): Result<String> {
        return try {
            val request = RegisterRequestDto(login, email, password, fullName)
            val response = apiService.register(request)

            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                tokenManager.saveTokens(
                    authResponse.accessToken,
                    authResponse.refreshToken,
                    authResponse.expiresAt
                )
                tokenManager.saveUserData(authResponse.id, authResponse.email, authResponse.login)
                Result.success("Registration successful")
            } else {
                Result.failure(Exception("Registration failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(): Result<String> {
        return try {
            apiService.logout()
            tokenManager.clearTokens()
            Result.success("Logout successful")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCurrentUser(): Result<String> {
        return try {
            val response = apiService.getCurrentUser()
            if (response.isSuccessful) {
                Result.success(response.body()?.email ?: "Unknown")
            } else {
                Result.failure(Exception("Failed to get user"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

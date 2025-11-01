package com.example.budget_management_system.repositories

import android.content.Context
import com.example.budget_management_system.models.api.ApiService
import com.example.budget_management_system.models.dto.LoginRequest
import com.example.budget_management_system.models.dto.RegisterRequest
import com.example.budget_management_system.utils.TokenManager

class AuthRepository(
    private val apiService: ApiService,
    private val tokenManager: TokenManager,
    private val context: Context
) {
    suspend fun register(email: String, password: String, fullName: String): Boolean {
        return try {
            val request = RegisterRequest(email, password, fullName)
            val response = apiService.register(request)
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                tokenManager.saveTokens(authResponse.accessToken, authResponse.refreshToken, authResponse.expiresIn)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun login(email: String, password: String): Boolean {
        return try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                tokenManager.saveTokens(authResponse.accessToken, authResponse.refreshToken, authResponse.expiresIn)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun logout() {
        tokenManager.clearTokens()
    }

    fun isLoggedIn(): Boolean {
        return tokenManager.getAccessToken() != null && !tokenManager.isTokenExpired()
    }
}

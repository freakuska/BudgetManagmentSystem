package com.example.budget_management_system.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val username: String? = null
)

@Serializable
data class AuthResponse(
    val id: String,
    val email: String,
    val username: String? = null,
    val token: String? = null
)

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)

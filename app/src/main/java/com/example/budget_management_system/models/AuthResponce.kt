package com.example.budget_management_system.models

import com.example.budget_management_system.models.User

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: String,
    val user: User
)
package com.example.budget_management_system.models.dto

import java.util.*

data class AuthResponseDto(
    val id: UUID,
    val login: String,
    val email: String,
    val fullName: String?,
    val avatarUrl: String?,
    val isVerified: Boolean,
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Date,
    val tokenType: String = "Bearer"
)
package com.example.budget_management.models

data class AuthResponseDto(
    val user: User,
    val accessToken: String?,
    val refreshToken: String?
)

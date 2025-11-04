package com.example.budget_management_system.models

import java.util.*

data class RefreshToken(
    val id: UUID,
    val userId: UUID,
    val token: String,
    val expiresAt: Date,
    val createdAt: Date,
    val revokedAt: Date? = null,
    val user: User? = null
)
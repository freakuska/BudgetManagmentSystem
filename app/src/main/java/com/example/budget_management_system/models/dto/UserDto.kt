package com.example.budget_management_system.models.dto

import java.util.*

data class UserDto(
    val id: UUID,
    val login: String,
    val email: String,
    val fullName: String?,
    val phone: String?,
    val avatarUrl: String?,
    val isActive: Boolean,
    val isVerified: Boolean,
    val createdAt: Date,
    val updatedAt: Date,
    val lastLoginAt: Date?
)
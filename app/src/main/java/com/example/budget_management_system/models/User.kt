package com.example.budget_management_system.models

data class User(
    val id: String,
    val login: String,
    val email: String,
    val fullName: String?,
    val phone: String?,
    val avatarUrl: String?,
    val isActive: Boolean,
    val isVerified: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val lastLoginAt: String?
)
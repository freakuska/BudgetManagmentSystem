package com.example.budget_management_system.models

import java.util.*

data class Role(
    val id: UUID,
    val name: String,
    val code: String,
    val description: String,
    val permissions: String, // JSON array as string
    val isSystem: Boolean = false,
    val createdAt: Date,
    val updatedAt: Date,
    val userRoles: List<UserRole> = emptyList()
)
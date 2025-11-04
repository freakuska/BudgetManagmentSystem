package com.example.budget_management_system.models.dto

import java.util.*

data class RoleDto(
    val id: UUID,
    val name: String,
    val code: String,
    val description: String,
    val permissions: List<String>,
    val isSystem: Boolean,
    val createdAt: Date,
    val updatedAt: Date
)
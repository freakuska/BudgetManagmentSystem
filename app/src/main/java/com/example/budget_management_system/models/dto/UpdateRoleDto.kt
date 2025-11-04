package com.example.budget_management_system.models.dto

data class UpdateRoleDto(
    val name: String? = null,
    val code: String? = null,
    val description: String? = null,
    val permissions: List<String>? = null
)
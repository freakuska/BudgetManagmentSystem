package com.example.budget_management_system.models.dto

data class CreateRoleDto(
    val name: String,
    val code: String,
    val description: String,
    val permissions: List<String> = emptyList()
)
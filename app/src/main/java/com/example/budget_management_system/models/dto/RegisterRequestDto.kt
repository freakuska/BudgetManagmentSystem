package com.example.budget_management_system.models

data class RegisterRequestDto(
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)

package com.example.budget_management_system.models

data class RegisterRequest(
    val email: String,
    val password: String,
    val fullName: String? = null,
    val phone: String? = null
)
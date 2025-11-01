package com.example.budget_management_system.models

data class UpdateOperationDto(
    val type: String,
    val amount: Double,
    val date: String,
    val description: String? = null,
    val tagId: Int? = null
)

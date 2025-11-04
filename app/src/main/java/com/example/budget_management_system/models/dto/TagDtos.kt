package com.example.budget_management_system.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class TagRequest(
    val name: String,
    val type: String, // "Income", "Expense", "Transfer"
    val color: String? = null,
    val icon: String? = null
)

@Serializable
data class TagResponse(
    val id: String,
    val userId: String,
    val name: String,
    val type: String,
    val color: String? = null,
    val icon: String? = null,
    val createdAt: String
)

@Serializable
data class TagTreeResponse(
    val income: List<TagResponse>,
    val expense: List<TagResponse>,
    val transfer: List<TagResponse>
)

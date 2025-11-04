package com.example.budget_management_system.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class OperationRequest(
    val amount: Double,
    val description: String,
    val type: String, // "Income", "Expense", "Transfer"
    val tagId: String,
    val date: String // ISO format
)

@Serializable
data class OperationResponse(
    val id: String,
    val userId: String,
    val amount: Double,
    val description: String,
    val type: String,
    val tagId: String,
    val tag: TagResponse? = null,
    val date: String,
    val createdAt: String
)

@Serializable
data class OperationStats(
    val totalIncome: Double,
    val totalExpense: Double,
    val balance: Double,
    val operationsCount: Int
)

@Serializable
data class GetOperationsResponse(
    val items: List<OperationResponse>,
    val total: Int
)

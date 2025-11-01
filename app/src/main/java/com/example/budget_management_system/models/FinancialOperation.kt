package com.example.budget_management_system.models

import com.example.budget_management_system.models.Money

data class FinancialOperation(
    val id: String, // Guid -> String
    val createdAt: String, // ISO-8601 string
    val updatedAt: String,
    val deletedAt: String?, // nullable
    val ownerId: String,
    val createdBy: String,
    val updatedBy: String?,
    val type: String, // "income", "expense", "transfer"
    val description: String,
    val notes: String?,
    val paymentMethod: String, // "cash", "card", etc.
    val operationDateTime: String, // ISO-8601 string
    val money: Money
)

// Для удобства можно добавить extension-функции
fun FinancialOperation.toDisplayString(): String {
    return "$description - ${money.amount} ${money.currency}"
}
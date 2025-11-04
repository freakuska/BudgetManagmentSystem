package com.example.budget_management_system.models.dto

data class StatsDto(
    val totalIncome: Double,
    val totalExpense: Double,
    val balance: Double,
    val currency: String = "RUB",
    val operationsCount: Int,
    val incomeCount: Int,
    val expenseCount: Int,
    val transferCount: Int
)
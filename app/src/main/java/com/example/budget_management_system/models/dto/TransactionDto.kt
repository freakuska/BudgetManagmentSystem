package com.example.budget_management_system.models.dto

import java.io.Serializable
import java.util.*

data class TransactionDto(
    val id: UUID,
    val userId: String,
    val description: String,
    val amount: Double,
    val currency: String,
    val operationDateTime: Date,
    val type: String,
    val tags: List<TagDto> = emptyList()
) : Serializable

package com.example.budget_management_system.models.dto

import com.example.budget_management_system.enums.OperationType
import com.example.budget_management_system.enums.PaymentMethod
import java.util.*

data class FinancialOperationDto(
    val id: UUID,
    val type: OperationType,
    val description: String,
    val notes: String?,
    val paymentMethod: PaymentMethod,
    val operationDateTime: Date,
    val amount: Double,
    val currency: String,
    val createdAt: Date,
    val updatedAt: Date,
    val tags: List<TagDto> = emptyList()
)
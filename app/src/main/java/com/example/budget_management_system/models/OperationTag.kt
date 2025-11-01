package com.example.budget_management_system.models

import java.time.LocalDateTime

data class OperationTag(
    val id: String,
    val tagId: String,
    val operationId: String,
    val createdAt: LocalDateTime,

    // Навигационные свойства
    val tag: Tag?,
    val operation: FinancialOperation?
)
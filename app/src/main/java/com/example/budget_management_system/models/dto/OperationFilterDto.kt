package com.example.budget_management_system.models.dto

import com.example.budget_management_system.enums.OperationType
import java.util.*

data class OperationFilterDto(
    val skip: Int = 0,
    val take: Int = 50,
    val type: OperationType? = null,
    val startDate: Date? = null,
    val endDate: Date? = null,
    val tagIds: List<UUID> = emptyList(),
    val sortBy: String = "operationDateTime",
    val descending: Boolean = true
)
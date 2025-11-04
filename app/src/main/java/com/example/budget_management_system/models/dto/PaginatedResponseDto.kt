package com.example.budget_management_system.models.dto

data class PaginatedResponseDto<T>(
    val items: List<T>,
    val total: Int,
    val page: Int,
    val pageSize: Int,
    val hasNextPage: Boolean = page * pageSize < total,
    val hasPreviousPage: Boolean = page > 0
)
package com.example.budget_management_system.models

data class Tag(
    val id: String,
    val name: String,
    val slug: String,
    val type: String, // "income", "expense", "transfer"
    val icon: String?,
    val color: String?,
    val parentId: String?,
    val level: Int?,
    val path: String?,
    val isActive: Boolean,
    val isSystem: Boolean,
    val ownerId: String?,
    val createdAt: String,
    val updatedAt: String,
    val sortOrder: Int?,
    val usageCount: Int,
    val visibility: String // "public" / "private"
)
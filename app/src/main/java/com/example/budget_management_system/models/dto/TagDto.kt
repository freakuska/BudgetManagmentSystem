package com.example.budget_management_system.models.dto

import com.example.budget_management_system.enums.TagType
import com.example.budget_management_system.enums.TagVisibility
import java.util.*

data class TagDto(
    val id: UUID,
    val name: String,
    val slug: String,
    val type: TagType,
    val icon: String?,
    val color: String?,
    val parentId: UUID?,
    val level: Int?,
    val path: String?,
    val isActive: Boolean,
    val isSystem: Boolean,
    val usageCount: Int,
    val visibility: TagVisibility,
    val childTags: List<TagDto> = emptyList()
)
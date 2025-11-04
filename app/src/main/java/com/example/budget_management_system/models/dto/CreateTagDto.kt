package com.example.budget_management_system.models.dto

import com.example.budget_management_system.enums.TagType
import com.example.budget_management_system.enums.TagVisibility
import java.util.*

data class CreateTagDto(
    val name: String,
    val type: TagType,
    val icon: String? = null,
    val color: String? = null,
    val parentId: UUID? = null,
    val visibility: TagVisibility = TagVisibility.Private
)
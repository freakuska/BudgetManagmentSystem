package com.example.budget_management_system.models.dto

import com.example.budget_management_system.enums.TagType
import com.example.budget_management_system.enums.TagVisibility
import java.util.*

data class UpdateTagDto(
    val name: String? = null,
    val type: TagType? = null,
    val icon: String? = null,
    val color: String? = null,
    val parentId: UUID? = null,
    val visibility: TagVisibility? = null
)

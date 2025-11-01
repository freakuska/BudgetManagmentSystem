package com.example.budget_management_system.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budget_management_system.enums.TagType
import java.util.*

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val type: TagType,
    val color: String? = "#FF6B6B",
    val usageCount: Int = 0,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val ownerId: UUID? = null
)

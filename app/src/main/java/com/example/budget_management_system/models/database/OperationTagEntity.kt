package com.example.budget_management_system.models.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "operation_tags",
    foreignKeys = [
        ForeignKey(
            entity = OperationEntity::class,
            parentColumns = ["id"],
            childColumns = ["operationId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OperationTagEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val operationId: UUID,
    val tagId: UUID,
    val createdAt: Date = Date()
)

package com.example.budget_management_system.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budget_management_system.enums.OperationType
import java.util.*

@Entity(tableName = "operations")
data class OperationEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val description: String,
    val amount: Double,
    val currency: String = "RUB",
    val type: OperationType,
    val operationDateTime: Date,
    val notes: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val ownerId: UUID? = null
)

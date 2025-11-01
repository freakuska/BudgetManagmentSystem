package com.example.budget_management_system.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val fullName: String,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

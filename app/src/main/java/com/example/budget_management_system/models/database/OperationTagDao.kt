package com.example.budget_management_system.models.database

import androidx.room.*
import java.util.*

@Dao
interface OperationTagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOperationTag(operationTag: OperationTagEntity)

    @Delete
    suspend fun deleteOperationTag(operationTag: OperationTagEntity)

    @Query("DELETE FROM operation_tags WHERE operationId = :operationId")
    suspend fun deleteByOperationId(operationId: UUID)

    @Query("DELETE FROM operation_tags WHERE tagId = :tagId")
    suspend fun deleteByTagId(tagId: UUID)

    @Query("SELECT * FROM operation_tags WHERE operationId = :operationId")
    suspend fun getTagsByOperationId(operationId: UUID): List<OperationTagEntity>

    @Query("SELECT * FROM operation_tags WHERE tagId = :tagId")
    suspend fun getOperationsByTagId(tagId: UUID): List<OperationTagEntity>
}

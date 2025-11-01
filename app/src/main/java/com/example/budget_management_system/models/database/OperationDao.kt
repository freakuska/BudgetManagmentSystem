package com.example.budget_management_system.models.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface OperationDao {
    @Query("SELECT * FROM operations ORDER BY operationDateTime DESC")
    fun getAllOperations(): Flow<List<OperationEntity>>

    @Query("SELECT * FROM operations WHERE id = :id")
    suspend fun getOperationById(id: UUID): OperationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOperation(operation: OperationEntity)

    @Update
    suspend fun updateOperation(operation: OperationEntity)

    @Delete
    suspend fun deleteOperation(operation: OperationEntity)

    @Query("DELETE FROM operations WHERE id = :id")
    suspend fun deleteOperationById(id: UUID)

    @Query("SELECT * FROM operations WHERE type = :type ORDER BY operationDateTime DESC")
    fun getOperationsByType(type: String): Flow<List<OperationEntity>>

    @Query("SELECT * FROM operations WHERE operationDateTime >= :startDate AND operationDateTime <= :endDate ORDER BY operationDateTime DESC")
    fun getOperationsByDateRange(startDate: Date, endDate: Date): Flow<List<OperationEntity>>
}

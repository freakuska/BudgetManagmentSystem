package com.example.budget_management_system.repositories

import com.example.budget_management_system.models.api.ApiService
import com.example.budget_management_system.models.database.OperationDao
import com.example.budget_management_system.models.database.OperationEntity
import com.example.budget_management_system.models.dto.FinancialOperationDto
import kotlinx.coroutines.flow.Flow
import java.util.*

class OperationRepository(
    private val apiService: ApiService,
    private val operationDao: OperationDao
) {
    fun getAllOperations(): Flow<List<OperationEntity>> {
        return operationDao.getAllOperations()
    }

    suspend fun getOperationById(id: UUID): OperationEntity? {
        return operationDao.getOperationById(id)
    }

    suspend fun fetchOperationsFromServer() {
        try {
            val response = apiService.getOperations()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach { dto ->
                    val entity = OperationEntity(
                        id = dto.id,
                        description = dto.description,
                        amount = dto.amount,
                        currency = dto.currency,
                        type = dto.type,
                        operationDateTime = dto.operationDateTime,
                        notes = dto.notes
                    )
                    operationDao.insertOperation(entity)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun createOperation(operation: FinancialOperationDto) {
        try {
            val response = apiService.createOperation(operation)
            if (response.isSuccessful && response.body() != null) {
                val entity = OperationEntity(
                    id = response.body()!!.id,
                    description = response.body()!!.description,
                    amount = response.body()!!.amount,
                    currency = response.body()!!.currency,
                    type = response.body()!!.type,
                    operationDateTime = response.body()!!.operationDateTime,
                    notes = response.body()!!.notes
                )
                operationDao.insertOperation(entity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateOperation(id: UUID, operation: FinancialOperationDto) {
        try {
            val response = apiService.updateOperation(id, operation)
            if (response.isSuccessful) {
                val entity = OperationEntity(
                    id = operation.id,
                    description = operation.description,
                    amount = operation.amount,
                    currency = operation.currency,
                    type = operation.type,
                    operationDateTime = operation.operationDateTime,
                    notes = operation.notes
                )
                operationDao.updateOperation(entity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteOperation(id: UUID) {
        try {
            val response = apiService.deleteOperation(id)
            if (response.isSuccessful) {
                operationDao.deleteOperationById(id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

package com.example.budget_management_system.repositories

import com.example.budget_management_system.models.api.ApiService
import com.example.budget_management_system.models.database.OperationDao
import com.example.budget_management_system.models.dto.CreateOperationDto
import com.example.budget_management_system.models.FinancialOperation
import com.example.budget_management_system.models.dto.FinancialOperationDto
import com.example.budget_management_system.models.dto.StatsDto
import com.example.budget_management_system.models.dto.UpdateOperationDto

class TransactionRepository(
    private val apiService: ApiService,
    private val operationDao: OperationDao
) {

    suspend fun getOperations(skip: Int = 0, take: Int = 50): Result<List<FinancialOperationDto>> {
        return try {
            val response = apiService.getOperations(skip, take)
            if (response.isSuccessful && response.body() != null) {
                val operations = response.body()!!.items
                operations.forEach { operationDao.insert(it) }
                Result.success(operations)
            } else {
                Result.failure(Exception("Failed to load operations"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getOperation(id: String): Result<FinancialOperationDto> {
        return try {
            val response = apiService.getOperation(id)
            if (response.isSuccessful && response.body() != null) {
                val operation = response.body()!!
                operationDao.insert(operation)
                Result.success(operation)
            } else {
                Result.failure(Exception("Failed to load operation"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createOperation(
        amount: Double,
        description: String,
        type: String,
        tagId: String,
        date: String
    ): Result<FinancialOperationDto> {
        return try {
            val request = CreateOperationDto(amount, description, type, tagId, date)
            val response = apiService.createOperation(request)
            if (response.isSuccessful && response.body() != null) {
                val operation = response.body()!!
                operationDao.insert(operation)
                Result.success(operation)
            } else {
                Result.failure(Exception("Failed to create operation"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateOperation(
        id: String,
        amount: Double,
        description: String,
        type: String,
        tagId: String,
        date: String
    ): Result<FinancialOperationDto> {
        return try {
            val request = UpdateOperationDto(amount, description, type, tagId, date)
            val response = apiService.updateOperation(id, request)
            if (response.isSuccessful && response.body() != null) {
                val operation = response.body()!!
                operationDao.update(operation)
                Result.success(operation)
            } else {
                Result.failure(Exception("Failed to update operation"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteOperation(id: String): Result<Unit> {
        return try {
            apiService.deleteOperation(id)
            operationDao.deleteById(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getStats(): Result<StatsDto> {
        return try {
            val response = apiService.getStats()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to load stats"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLocalOperations(limit: Int = 50, offset: Int = 0): Result<List<FinancialOperation>> {
        return try {
            val operations = operationDao.getAll(limit, offset)
            Result.success(operations)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

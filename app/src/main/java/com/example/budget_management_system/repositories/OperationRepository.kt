package com.example.budget_management_system.repositories

import com.example.budget_management_system.models.api.ApiService
import com.example.budget_management_system.models.database.OperationDao
import com.example.budget_management_system.models.dto.CreateOperationDto
import com.example.budget_management_system.models.dto.FinancialOperationDto
import com.example.budget_management_system.models.dto.UpdateOperationDto
import java.util.*

class OperationRepository(
    private val apiService: ApiService,
    private val operationDao: OperationDao
) {

    suspend fun getOperations(skip: Int = 0, take: Int = 50): Result<List<FinancialOperationDto>> {
        return try {
            val response = apiService.getOperations(skip, take)
            if (response.isSuccessful && response.body() != null) {
                val operations = response.body()!!.items
                Result.success(operations)
            } else {
                Result.failure(Exception("Failed to load operations"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getOperation(id: UUID): Result<FinancialOperationDto> {
        return try {
            val response = apiService.getOperation(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to load operation"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createOperation(
        type: String,
        description: String,
        notes: String? = null,
        paymentMethod: String,
        operationDateTime: Date,
        amount: Double,
        currency: String = "RUB",
        tagIds: List<UUID> = emptyList()
    ): Result<FinancialOperationDto> {
        return try {
            val request = CreateOperationDto(

                type = com.example.budget_management_system.models.api.OperationType.valueOf(type),
                description = description,
                notes = notes,
                paymentMethod = com.example.budget_management_system.enums.PaymentMethod.valueOf(paymentMethod),
                operationDateTime = operationDateTime,
                tagIds = tagIds,
            )
            val response = apiService.createOperation(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to create operation"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateOperation(
        id: UUID,
        type: String? = null,
        description: String? = null,
        notes: String? = null,
        paymentMethod: String? = null,
        operationDateTime: Date? = null,
        amount: Double? = null,
        currency: String? = null,
        tagIds: List<UUID>? = null
    ): Result<FinancialOperationDto> {
        return try {
            val request = UpdateOperationDto(
                type = type?.let { com.example.budget_management_system.models.api.OperationType.valueOf(it) },
                description = description,
                notes = notes,
                paymentMethod = paymentMethod?.let { com.example.budget_management_system.enums.PaymentMethod.valueOf(it) },
                operationDateTime = operationDateTime,
                amount = amount,
                currency = currency,
                tagIds = tagIds
            )
            val response = apiService.updateOperation(id, request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to update operation"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteOperation(id: UUID): Result<Unit> {
        return try {
            apiService.deleteOperation(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun restoreOperation(id: UUID): Result<Unit> {
        return try {
            apiService.restoreOperation(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getStats(): Result<Map<String, Any>> {
        return try {
            val response = apiService.getStats()
            if (response.isSuccessful && response.body() != null) {
                val stats = response.body()!!
                val result = mapOf(
                    "totalIncome" to stats.totalIncome,
                    "totalExpense" to stats.totalExpense,
                    "balance" to stats.balance,
                    "currency" to stats.currency,
                    "operationsCount" to stats.operationsCount
                )
                Result.success(result)
            } else {
                Result.failure(Exception("Failed to load stats"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

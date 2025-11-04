package com.example.budget_management_system.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget_management_system.models.dto.OperationResponse
import com.example.budget_management_system.models.dto.OperationStats
import com.example.budget_management_system.repositories.OperationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class OperationUiState(
    val operations: List<OperationResponse> = emptyList(),
    val stats: OperationStats? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedOperation: OperationResponse? = null
)

class OperationViewModel(private val operationRepository: OperationRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(OperationUiState())
    val uiState = _uiState.asStateFlow()

    fun loadOperations(skip: Int = 0, take: Int = 50) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = operationRepository.getOperations(skip, take)
            result.onSuccess { operations ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    operations = operations
                )
            }
            result.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Failed to load operations"
                )
            }
        }
    }

    fun loadStats() {
        viewModelScope.launch {
            val result = operationRepository.getStats()
            result.onSuccess { stats ->
                _uiState.value = _uiState.value.copy(stats = stats)
            }
            result.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    error = error.message ?: "Failed to load stats"
                )
            }
        }
    }

    fun createOperation(
        amount: Double,
        description: String,
        type: String,
        tagId: String,
        date: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = operationRepository.createOperation(amount, description, type, tagId, date)
            result.onSuccess { operation ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    operations = listOf(operation) + _uiState.value.operations
                )
                loadStats()
            }
            result.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Failed to create operation"
                )
            }
        }
    }

    fun updateOperation(
        id: String,
        amount: Double,
        description: String,
        type: String,
        tagId: String,
        date: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = operationRepository.updateOperation(id, amount, description, type, tagId, date)
            result.onSuccess { operation ->
                val updatedList = _uiState.value.operations.map {
                    if (it.id == id) operation else it
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    operations = updatedList
                )
                loadStats()
            }
            result.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Failed to update operation"
                )
            }
        }
    }

    fun deleteOperation(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = operationRepository.deleteOperation(id)
            result.onSuccess {
                val updatedList = _uiState.value.operations.filter { it.id != id }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    operations = updatedList
                )
                loadStats()
            }
            result.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Failed to delete operation"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

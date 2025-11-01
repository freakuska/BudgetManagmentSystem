package com.example.budget_management_system.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget_management_system.models.database.OperationEntity
import com.example.budget_management_system.models.dto.FinancialOperationDto
import com.example.budget_management_system.repositories.OperationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class OperationViewModel(private val repository: OperationRepository) : ViewModel() {
    private val _operations = MutableStateFlow<List<OperationEntity>>(emptyList())
    val operations: StateFlow<List<OperationEntity>> = _operations.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadOperations()
    }

    fun loadOperations() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.fetchOperationsFromServer()
                repository.getAllOperations().collect { ops ->
                    _operations.value = ops
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createOperation(operation: FinancialOperationDto) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.createOperation(operation)
                loadOperations()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteOperation(id: UUID) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deleteOperation(id)
                loadOperations()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}

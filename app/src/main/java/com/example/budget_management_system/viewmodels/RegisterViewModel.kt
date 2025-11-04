package com.example.budget_management_system.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget_management_system.ApiService
import com.example.budget_management_system.models.dto.AuthResponseDto
import com.example.budget_management_system.models.dto.RegisterRequestDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

sealed class RegisterState {
    object Loading : RegisterState()
    data class Success(val user: AuthResponseDto) : RegisterState()
    data class Error(val message: String) : RegisterState()
}

class RegisterViewModel(private val apiService: ApiService) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Loading)
    val registerState: StateFlow<RegisterState> = _registerState

    fun register(email: String, password: String, fullName: String?) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            try {
                Log.d("RegisterViewModel", "Trying to register with email: $email")
                val response = apiService.register(RegisterRequestDto(email, password, fullName))
                Log.d("RegisterViewModel", "Register successful: $response")
                _registerState.value = RegisterState.Success(response)
            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Register failed", e)
                _registerState.value = RegisterState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}
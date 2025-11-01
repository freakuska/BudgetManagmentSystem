package com.example.budget_management_system

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.budget_management_system.ApiService
import com.example.budget_management_system.viewmodels.RegisterViewModel

class RegisterViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
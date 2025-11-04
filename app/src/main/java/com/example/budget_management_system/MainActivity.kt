package com.example.budget_management_system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Label
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.budget_management_system.repositories.AuthRepository
import com.example.budget_management_system.repositories.OperationRepository
import com.example.budget_management_system.repositories.TagRepository
import com.example.budget_management_system.models.security.TokenManager
import com.example.budget_management_system.models.navigation.NavigationHost
import com.example.budget_management_system.models.theme.FinanceAppTheme
import com.example.budget_management_system.viewmodels.AuthViewModel
import com.example.budget_management_system.viewmodels.OperationViewModel
import com.example.budget_management_system.viewmodels.TagViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = RetrofitClient.createApiService(this)
        val tokenManager = TokenManager(this)

        val authRepository = AuthRepository(apiService, tokenManager)
        val operationRepository = OperationRepository(apiService)
        val tagRepository = TagRepository(apiService)

        val authViewModel = AuthViewModel(authRepository)
        val operationViewModel = OperationViewModel(operationRepository)
        val tagViewModel = TagViewModel(tagRepository)

        setContent {
            FinanceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val authState by authViewModel.uiState.collectAsState()

                    Box(modifier = Modifier.fillMaxSize()) {
                        NavigationHost(
                            navController = navController,
                            authViewModel = authViewModel,
                            operationViewModel = operationViewModel,
                            tagViewModel = tagViewModel,
                            isLoggedIn = authState.isLoggedIn
                        )

                        // Bottom Navigation
                        if (authState.isLoggedIn) {
                            NavigationBar(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                            ) {
                                NavigationBarItem(
                                    icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
                                    label = { Text("Dashboard") },
                                    selected = navController.currentDestination?.route == "dashboard",
                                    onClick = {
                                        navController.navigate("dashboard") {
                                            popUpTo("dashboard") { inclusive = true }
                                        }
                                    }
                                )

                                NavigationBarItem(
                                    icon = { Icon(Icons.Default.Label, contentDescription = "Tags") },
                                    label = { Text("Tags") },
                                    selected = navController.currentDestination?.route == "tags",
                                    onClick = {
                                        navController.navigate("tags")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

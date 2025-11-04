package com.example.budget_management_system.models.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.budget_management_system.ui.DashboardScreen
import com.example.budget_management_system.ui.LoginScreen
import com.example.budget_management_system.ui.RegisterScreen
import com.example.budget_management_system.ui.TagsScreen
import com.example.budget_management_system.viewmodels.AuthViewModel
import com.example.budget_management_system.viewmodels.OperationViewModel
import com.example.budget_management_system.viewmodels.TagViewModel

@Composable
fun NavigationHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    operationViewModel: OperationViewModel,
    tagViewModel: TagViewModel,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "dashboard" else "login"
    ) {
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable("dashboard") {
            DashboardScreen(
                operationViewModel = operationViewModel,
                tagViewModel = tagViewModel,
                authViewModel = authViewModel,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                }
            )
        }

        composable("tags") {
            TagsScreen(tagViewModel = tagViewModel)
        }
    }
}

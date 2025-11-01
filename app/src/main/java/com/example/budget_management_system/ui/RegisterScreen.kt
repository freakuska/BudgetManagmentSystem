package com.example.budget_management_system.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budget_management_system.viewmodels.RegisterViewModel
import com.example.budget_management_system.viewmodels.RegisterState

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: RegisterViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Регистрация",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground, // ❗️ Белый текст
            modifier = Modifier.padding(bottom = 20.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = MaterialTheme.colors.onBackground) }, // ❗️ Белый текст
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль", color = MaterialTheme.colors.onBackground) }, // ❗️ Белый текст
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Полное имя", color = MaterialTheme.colors.onBackground) }, // ❗️ Белый текст
            modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
        )

        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    return@Button
                }
                viewModel.register(email, password, fullName)
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        ) {
            Text("Зарегистрироваться", color = MaterialTheme.colors.onBackground) // ❗️ Белый текст
        }

        TextButton(onClick = onBackClick) {
            Text("Назад", color = MaterialTheme.colors.onBackground) // ❗️ Белый текст
        }

        // Обработка состояния регистрации
        when (val state = viewModel.registerState.collectAsState().value) {
            is RegisterState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top = 20.dp))
            }
            is RegisterState.Success -> {
                LaunchedEffect(Unit) {
                    onRegisterSuccess()
                }
            }
            is RegisterState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colors.error, // ❗️ Ошибка остаётся красной для видимости
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
        }
    }
}
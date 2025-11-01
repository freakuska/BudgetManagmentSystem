package com.example.budget_management_system.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import com.example.budget_management_system.viewmodels.LoginViewModel
import com.example.budget_management_system.viewmodels.LoginState

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Белый фон
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ВХОД",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black, // Чёрный текст
            modifier = Modifier.padding(bottom = 20.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.Black) }, // Чёрный текст
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль", color = Color.Black) }, // Чёрный текст
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
        )

        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    return@Button
                }
                viewModel.login(email, password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF6200EE), // Фиолетовый цвет кнопки
                contentColor = Color.White // Белый текст на кнопке
            )
        ) {
            Text("ВОЙТИ")
        }

        TextButton(
            onClick = onRegisterClick,
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Text(
                text = "Зарегистрироваться",
                color = Color(0xFFFF6F00), // Оранжевый цвет текста
                style = MaterialTheme.typography.body1.copy(
                    textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                )
            )
        }

        // Обработка состояния входа
        when (val state = viewModel.loginState.collectAsState().value) {
            is LoginState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top = 20.dp))
            }
            is LoginState.Success -> {
                LaunchedEffect(Unit) {
                    onLoginSuccess()
                }
            }
            is LoginState.Error -> {
                Text(
                    text = state.message,
                    color = Color.Red, // Красный текст для ошибки
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
        }
    }
}
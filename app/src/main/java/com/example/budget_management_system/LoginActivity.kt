package com.example.budget_management_system

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.budget_management_system.models.api.RetrofitClient
import com.example.budget_management_system.repositories.AuthRepository
import com.example.budget_management_system.utils.TokenManager
import com.example.budget_management_system.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var authViewModel: AuthViewModel
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerTextButton)
        progressBar = findViewById(R.id.progressBar)

        val tokenManager = TokenManager(this)
        val apiService = RetrofitClient.getInstance(this).create(
            com.example.budget_management_system.models.api.ApiService::class.java
        )
        val repository = AuthRepository(apiService, tokenManager, this)
        authViewModel = ViewModelProvider.NewInstanceFactory().create(AuthViewModel::class.java)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authViewModel.login(email, password)
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        lifecycleScope.launch {
            authViewModel.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }
        }

        lifecycleScope.launch {
            authViewModel.isLoading.collect { isLoading ->
                progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
            }
        }

        lifecycleScope.launch {
            authViewModel.errorMessage.collect { message ->
                if (message != null) {
                    Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

package com.example.budget_management_system

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.budget_management_system.repositories.AuthRepository
import com.example.budget_management_system.utils.TokenManager
import com.example.budget_management_system.utils.SessionManager
import com.example.budget_management_system.viewmodels.AuthViewModel
import com.example.budget_management_system.viewmodels.AuthViewModelFactory
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity() {

    private val tokenManager by lazy { TokenManager(this) }
    private val sessionManager by lazy { SessionManager(this) }
    private val apiService by lazy { RetrofitClient.getApiService(this) }
    private val authRepository by lazy { AuthRepository(apiService, tokenManager, sessionManager) }

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModel(authRepository)
    }

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextButton: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Присваиваем View
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerTextButton = findViewById(R.id.registerTextButton)
        progressBar = findViewById(R.id.progressBar)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                showError("Please fill all fields")
            }
        }

        registerTextButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when {
                    state.isLoading -> {
                        loginButton.isEnabled = false
                        progressBar.visibility = View.VISIBLE
                    }
                    state.error != null -> {
                        loginButton.isEnabled = true
                        progressBar.visibility = View.GONE
                        showError(state.error)
                    }
                    state.isLoggedIn -> {
                        loginButton.isEnabled = true
                        progressBar.visibility = View.GONE
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    private fun showError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

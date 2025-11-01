package com.example.budget_management_system

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.budget_management_system.utils.SessionManager

open class BaseActivity : AppCompatActivity() {

    protected val sessionManager by lazy { SessionManager(this) }

    // Метод для проверки авторизации
    protected fun isLoggedIn(): Boolean {
        return sessionManager.isLoggedIn.value
    }

    // Метод для получения ID пользователя
    protected fun getUserId(): String? {
        return sessionManager.getUserId()
    }
}
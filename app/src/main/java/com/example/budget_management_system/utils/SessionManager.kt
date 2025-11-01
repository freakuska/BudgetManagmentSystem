package com.example.budget_management_system.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionManager(private val context: Context) {

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "app_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun saveTokens(accessToken: String, refreshToken: String) {
        with(sharedPreferences.edit()) {
            putString("access_token", accessToken)
            putString("refresh_token", refreshToken)
            apply()
        }
        _isLoggedIn.value = true
    }

    fun getAccessToken(): String? = sharedPreferences.getString("access_token", null)

    fun getRefreshToken(): String? = sharedPreferences.getString("refresh_token", null)

    fun clearTokens() {
        with(sharedPreferences.edit()) {
            remove("access_token")
            remove("refresh_token")
            apply()
        }
        _isLoggedIn.value = false
    }

    fun getUserId(): String? {
        val token = getAccessToken() ?: return null
        return extractUserIdFromToken(token)
    }

    private fun extractUserIdFromToken(token: String): String? {
        try {
            val parts = token.split(".")
            if (parts.size < 2) return null

            val payload = String(android.util.Base64.decode(parts[1], android.util.Base64.DEFAULT))
            val jsonObject = org.json.JSONObject(payload)
            return jsonObject.optString("userId", null)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
package com.example.budget_management_system.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

private val Context.dataStore by preferencesDataStore(name = "auth_tokens")

class TokenManager(private val context: Context) {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_LOGIN_KEY = stringPreferencesKey("user_login")
        private val TOKEN_EXPIRY_KEY = stringPreferencesKey("token_expiry")
    }

    fun getAccessToken(): String? = null // Call from coroutine

    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String,
        expiresAt: Date
    ) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
            preferences[REFRESH_TOKEN_KEY] = refreshToken
            preferences[TOKEN_EXPIRY_KEY] = expiresAt.time.toString()
        }
    }

    suspend fun saveUserData(userId: UUID, email: String, login: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId.toString()
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_LOGIN_KEY] = login
        }
    }

    fun getAccessTokenFlow(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }
    }

    fun getRefreshTokenFlow(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN_KEY]
        }
    }

    fun getUserIdFlow(): Flow<UUID?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]?.let { UUID.fromString(it) }
        }
    }

    fun getUserEmailFlow(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_EMAIL_KEY]
        }
    }

    fun getUserLoginFlow(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_LOGIN_KEY]
        }
    }

    suspend fun clearTokens() {
        context.dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
            preferences.remove(USER_ID_KEY)
            preferences.remove(USER_EMAIL_KEY)
            preferences.remove(USER_LOGIN_KEY)
            preferences.remove(TOKEN_EXPIRY_KEY)
        }
    }
}

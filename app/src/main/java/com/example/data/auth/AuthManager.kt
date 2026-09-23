package com.example.data.auth

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UserSession(
    val isLoggedIn: Boolean,
    val username: String,
    val email: String,
    val role: String = "Clinical Researcher / Student"
)

class AuthManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("parkinson_auth_prefs", Context.MODE_PRIVATE)

    private val _sessionState = MutableStateFlow(loadSession())
    val sessionState: StateFlow<UserSession> = _sessionState.asStateFlow()

    private fun loadSession(): UserSession {
        val isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        val username = prefs.getString(KEY_USERNAME, "Clinical Researcher") ?: "Clinical Researcher"
        val email = prefs.getString(KEY_EMAIL, "researcher@medical-ai.edu") ?: "researcher@medical-ai.edu"
        return UserSession(isLoggedIn, username, email)
    }

    fun login(emailOrUsername: String, rememberMe: Boolean) {
        val username = if (emailOrUsername.contains("@")) {
            emailOrUsername.substringBefore("@").replace(".", " ").capitalizeWords()
        } else {
            emailOrUsername.capitalizeWords()
        }
        val email = if (emailOrUsername.contains("@")) emailOrUsername else "$emailOrUsername@medical-ai.edu"

        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_USERNAME, username)
            putString(KEY_EMAIL, email)
            putBoolean(KEY_REMEMBER_ME, rememberMe)
            apply()
        }
        _sessionState.value = UserSession(isLoggedIn = true, username = username, email = email)
    }

    fun logout() {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, false)
            apply()
        }
        _sessionState.value = UserSession(isLoggedIn = false, username = "", email = "")
    }

    fun isRemembered(): Boolean {
        return prefs.getBoolean(KEY_REMEMBER_ME, true)
    }

    private fun String.capitalizeWords(): String {
        return split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
    }

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_REMEMBER_ME = "remember_me"
    }
}

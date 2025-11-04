package com.example.proxservices.ui.screen.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proxservices.data.repository.AuthRepository
import kotlinx.coroutines.launch

// 1. Definimos el estado de la UI
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    // CAMBIO: 'loginSuccess' ahora guarda el ID del usuario
    val loggedInUserId: String? = null
)

// 2. El ViewModel
class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    // --- Eventos (Lo que la UI puede hacer) ---
    fun onEmailChange(email: String) {
        uiState = uiState.copy(email = email, errorMessage = null)
    }

    fun onPasswordChange(password: String) {
        uiState = uiState.copy(password = password, errorMessage = null)
    }

    fun onLoginClick() {
        if (uiState.isLoading) return
        uiState = uiState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            val result = authRepository.login(uiState.email, uiState.password)

            // CAMBIO: Verificamos el resultado de la simulación
            result.fold(
                onSuccess = { userId -> // <-- 'userId' viene del repositorio
                    uiState = uiState.copy(
                        isLoading = false,
                        loggedInUserId = userId // <-- Guardamos el ID
                    )
                },
                onFailure = { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Error desconocido"
                    )
                }
            )
        }
    }

    // --- Factory ---
    // (Esto se queda igual, pero es necesario)
    class Factory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(authRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

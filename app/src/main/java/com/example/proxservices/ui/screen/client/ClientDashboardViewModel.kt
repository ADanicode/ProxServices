package com.example.proxservices.ui.screen.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ClientDashboardState(
    val isLoading: Boolean = true,
    val userName: String = ""
)

class ClientDashboardViewModel(private val userId: String) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientDashboardState())
    val uiState = _uiState.asStateFlow()

    init {
        // En cuanto el ViewModel se crea, busca el nombre del usuario
        fetchUserData()
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            delay(1000) // Simula una búsqueda en la base de datos

            // Lógica simulada de tu equipo
            val name = if (userId == "user_client_001") {
                "Juan (Cliente)" // ¡El nombre que se mostrará!
            } else {
                "Usuario Desconocido"
            }

            _uiState.value = ClientDashboardState(isLoading = false, userName = name)
        }
    }

    // Factory para pasar el userId al ViewModel
    class Factory(private val userId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ClientDashboardViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ClientDashboardViewModel(userId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

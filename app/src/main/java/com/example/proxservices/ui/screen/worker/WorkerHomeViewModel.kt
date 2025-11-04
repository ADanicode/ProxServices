package com.example.proxservices.ui.screen.worker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proxservices.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Estado de la UI para la pantalla de inicio del trabajador
data class WorkerHomeState(
    val isLoading: Boolean = true,
    val userName: String = ""
)

class WorkerHomeViewModel(
    private val userId: String,
    private val authRepository: AuthRepository // Usamos la interfaz
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkerHomeState())
    val uiState = _uiState.asStateFlow()

    init {
        // En cuanto el ViewModel se crea, busca el nombre del usuario
        fetchUserName()
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            val result = authRepository.getUserName(userId)
            result.fold(
                onSuccess = { name ->
                    _uiState.value = WorkerHomeState(isLoading = false, userName = name)
                },
                onFailure = {
                    _uiState.value = WorkerHomeState(isLoading = false, userName = "Trabajador")
                }
            )
        }
    }

    // Factory para poder pasar el userId y el repositorio al ViewModel
    class Factory(
        private val userId: String,
        private val authRepository: AuthRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WorkerHomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WorkerHomeViewModel(userId, authRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


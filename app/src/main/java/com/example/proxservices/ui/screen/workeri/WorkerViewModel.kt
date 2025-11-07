package com.example.proxservices.ui.screen.workeri

import androidx.lifecycle.*
import com.example.proxservices.data.repository.AuthRepository
import kotlinx.coroutines.launch

data class WorkerProfile(
    val name: String = "",
    val title: String = "Especialista",
    val walletBalance: Double = 0.0,
    val reputation: Double = 4.5,
    val totalReviews: Int = 12
)

class WorkerViewModel(
    private val userId: String,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isAvailable = MutableLiveData(true)
    val isAvailable: LiveData<Boolean> = _isAvailable

    private val _profile = MutableLiveData(WorkerProfile())
    val profile: LiveData<WorkerProfile> = _profile

    init {
        loadProfile()
    }

    fun toggleAvailability(value: Boolean) {
        _isAvailable.value = value
    }

    fun viewWalletHistory() {
        // Aquí podrías navegar o mostrar historial
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val result = authRepository.getUserName(userId)
            val name = result.getOrElse { "Trabajador Anónimo" }
            _profile.value = WorkerProfile(name = name)
        }
    }

    class Factory(
        private val userId: String,
        private val authRepository: AuthRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WorkerViewModel(userId, authRepository) as T
        }
    }
}
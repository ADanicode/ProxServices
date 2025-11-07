package com.example.proxservices.ui.screen.workeri

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Data class para simular los datos del perfil y la billetera del trabajador (WORKER DASHBOARD - FIGMA)
data class WorkerProfile(
    val name: String = "Juan Pérez",
    val title: String = "Plomero Profesional",
    val isAvailable: Boolean = true, // Estado inicial
    val walletBalance: Double = 1250.75, // Saldo inicial
    val reputation: Double = 4.8,
    val totalReviews: Int = 127
)

class WorkerViewModel : ViewModel() {
    // LiveData para manejar el estado del perfil y notificar a la UI
    private val _profile = MutableLiveData(WorkerProfile())
    val profile: LiveData<WorkerProfile> = _profile

    // LiveData para manejar el estado de disponibilidad del trabajador
    private val _isAvailable = MutableLiveData(_profile.value?.isAvailable ?: true)
    val isAvailable: LiveData<Boolean> = _isAvailable

    /**
     * Alterna el estado de disponibilidad (Disponible/No Disponible)
     * y actualiza el LiveData del perfil.
     */
    fun toggleAvailability(available: Boolean) {
        _isAvailable.value = available
        _profile.value = _profile.value?.copy(isAvailable = available)
    }

    // --- Funcionalidades futuras para el Dashboard ---

    /**
     * Simula la navegación a la pantalla de historial de transacciones.
     */
    fun viewWalletHistory() {
        // Lógica de navegación o API call para historial aquí.
        println("Navegar a Historial de Billetera...")
    }

    /**
     * Simula la navegación a la pantalla de edición de perfil.
     */
    fun editProfile() {
        // Lógica de navegación aquí.
        println("Navegar a Editar Perfil...")
    }

    // Nota: El manejo de Servicios Pendientes (3 solicitudes por revisar) será lógica de Firestore.
}
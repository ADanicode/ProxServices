package com.example.proxservices.data.repository
import kotlinx.coroutines.delay

// 1. Definimos una "interfaz" (un contrato) de lo que puede hacer el repositorio
interface AuthRepository {
    // Usamos 'Result' para manejar éxito o error de forma segura
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun registerClient(fullName: String, email: String, pass: String): Result<Unit>
}

// 2. Creamos una implementación "simulada" de ese contrato
class SimulatedAuthRepository : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> {
        // Simulamos una llamada de red de 1.5 segundos
        delay(1500)

        // --- LÓGICA DE SIMULACIÓN ---
        return if (email == "cliente@prox.com" && password == "123456") {
            // Éxito
            Result.success(Unit)
        } else if (email == "worker@prox.com" && password == "123456") {
            // Éxito (podríamos devolver un tipo de usuario aquí)
            Result.success(Unit)
        } else {
            // Error
            Result.failure(Exception("Credenciales incorrectas."))
        }

        // --- DOCUMENTACIÓN PARA EL FUTURO (Como pediste) ---
        // TODO: Reemplazar esta simulación con una llamada real a tu API (Retrofit)
        /*
        try {
            val request = LoginRequest(email, password)
            val response = tuApiService.login(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error desconocido"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        */
    }

    override suspend fun registerClient(fullName: String, email: String, pass: String): Result<Unit> {
        // Simulamos una llamada de red
        delay(2000)

        if (email == "usado@prox.com") {
            return Result.failure(Exception("Este correo ya está en uso."))
        }

        // Éxito de registro
        return Result.success(Unit)

        // --- DOCUMENTACIÓN PARA EL FUTURO ---
        // TODO: Reemplazar con llamada real a tu API para registrar cliente
    }
}
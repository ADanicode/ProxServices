package com.example.proxservices.data.repository

import kotlinx.coroutines.delay

interface AuthRepository {
    // Devuelve el UserID en caso de éxito
    suspend fun login(email: String, password: String): Result<String>
    suspend fun registerClient(fullName: String, email: String, pass: String): Result<Unit>

    // Simula la búsqueda de un nombre de usuario por su ID
    suspend fun getUserName(userId: String): Result<String>
}

class SimulatedAuthRepository : AuthRepository {

    override suspend fun login(email: String, password: String): Result<String> {
        delay(1500)
        return when {
            email == "cliente@prox.com" && password == "123456" -> {
                Result.success("user_client_001") // ID de cliente simulado
            }
            email == "worker@prox.com" && password == "123456" -> {
                Result.success("user_worker_777") // ID de trabajador simulado
            }
            else -> {
                Result.failure(Exception("Credenciales incorrectas."))
            }
        }
    }

    override suspend fun registerClient(fullName: String, email: String, pass: String): Result<Unit> {
        delay(2000)
        return if (email == "usado@prox.com") {
            Result.failure(Exception("Este correo ya está en uso."))
        } else {
            Result.success(Unit)
        }
    }

    override suspend fun getUserName(userId: String): Result<String> {
        delay(500)
        return when (userId) {
            "user_client_001" -> Result.success("Juan Pérez (Cliente)")
            "user_worker_777" -> Result.success("María González (Worker)")
            else -> Result.failure(Exception("Usuario no encontrado"))
        }
    }
}

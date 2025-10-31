package com.example.proxservices.data.repository
import kotlinx.coroutines.delay


interface AuthRepository {

    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun registerClient(fullName: String, email: String, pass: String): Result<Unit>
}

class SimulatedAuthRepository : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> {

        delay(1500)


        return if (email == "cliente@prox.com" && password == "123456") {

            Result.success(Unit)
        } else if (email == "worker@prox.com" && password == "123456") {

            Result.success(Unit)
        } else {

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

        delay(2000)

        if (email == "usado@prox.com") {
            return Result.failure(Exception("Este correo ya está en uso."))
        }


        return Result.success(Unit)

    }
}
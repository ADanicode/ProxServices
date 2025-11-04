package com.example.proxservices.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proxservices.data.repository.SimulatedAuthRepository
import com.example.proxservices.ui.screen.auth.ForgotPasswordScreen
import com.example.proxservices.ui.screen.auth.LoginScreen
import com.example.proxservices.ui.screen.auth.LoginViewModel
import com.example.proxservices.ui.screen.auth.RegisterClientScreen
import com.example.proxservices.ui.screen.auth.RegisterWorkerScreen
import com.example.proxservices.ui.screen.client.ClientMainScreen
import com.example.proxservices.ui.screen.welcome.WelcomeScreen
import com.example.proxservices.ui.screen.worker.WorkerMainScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    // --- Inyección de Dependencias Simple (Simulación) ---
    // 1. Creamos una única instancia de nuestro repositorio simulado
    val authRepository = SimulatedAuthRepository()

    // 2. Creamos la Factory para el LoginViewModel
    val loginViewModelFactory = LoginViewModel.Factory(authRepository)

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route // Empezamos en la pantalla de Bienvenida
    ) {

        // --- Flujo de Autenticación ---

        composable(Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }

        composable(Screen.Login.route) {
            // Creamos el ViewModel y le pasamos la factory
            val loginViewModel: LoginViewModel = viewModel(factory = loginViewModelFactory)
            LoginScreen(navController = navController, viewModel = loginViewModel)
        }

        composable(Screen.RegisterClient.route) {
            RegisterClientScreen(navController = navController)
        }

        composable(Screen.RegisterWorker.route) {
            RegisterWorkerScreen(navController = navController)
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController = navController)
        }

        // --- Flujo Principal (Cliente) ---
        composable(
            route = Screen.ClientDashboard.route,
            arguments = Screen.ClientDashboard.arguments
        ) { backStackEntry ->
            // 1. Recibe el userId que el LoginScreen envió
            val userId = backStackEntry.arguments?.getString("userId") ?: "error_id_cliente"

            // 2. Llama al "Contenedor" principal del Cliente y le pasa todo
            ClientMainScreen(
                mainNavController = navController, // El navegador principal (para cerrar sesión)
                userId = userId,                   // El ID del usuario
                authRepository = authRepository    // El repositorio para los ViewModels internos
            )
        }

        // --- Flujo Principal (Trabajador) ---
        composable(
            route = Screen.WorkerDashboard.route,
            arguments = Screen.WorkerDashboard.arguments
        ) { backStackEntry ->
            // 1. Recibe el userId que el LoginScreen envió
            val userId = backStackEntry.arguments?.getString("userId") ?: "error_id_trabajador"

            // 2. Llama al "Contenedor" principal del Trabajador y le pasa todo
            WorkerMainScreen(
                mainNavController = navController, // El navegador principal (para cerrar sesión)
                userId = userId,                   // El ID del usuario
                authRepository = authRepository    // El repositorio para los ViewModels internos
            )
        }
    }
}

/**
 * Un Composable simple para rellenar pestañas que aún no hemos creado.
 */
@Composable
fun PlaceholderScreen(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text)
    }
}


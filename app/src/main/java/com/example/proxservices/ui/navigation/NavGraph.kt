package com.example.proxservices.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.proxservices.data.repository.SimulatedAuthRepository
import com.example.proxservices.ui.screen.auth.ForgotPasswordScreen
import com.example.proxservices.ui.screen.auth.LoginScreen
import com.example.proxservices.ui.screen.auth.LoginViewModel
import com.example.proxservices.ui.screen.auth.RegisterClientScreen
import com.example.proxservices.ui.screen.auth.RegisterWorkerScreen
import com.example.proxservices.ui.screen.welcome.WelcomeScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    // --- Inyección de Dependencias Simple (Simulación) ---
    // Creamos una única instancia de nuestro repositorio simulado
    // y la Factory para el ViewModel
    val authRepository = SimulatedAuthRepository()
    val loginViewModelFactory = LoginViewModel.Factory(authRepository)

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route // Empezamos en la pantalla de Bienvenida
    ) {
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
            // TODO: Crear ViewModel para esta pantalla
        }

        composable(Screen.RegisterWorker.route) {
            RegisterWorkerScreen(navController = navController)
            // TODO: Crear ViewModel para esta pantalla
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController = navController)
            // TODO: Crear ViewModel para esta pantalla
        }

        // --- Pantallas Futuras (Placeholders) ---
        composable(Screen.ClientDashboard.route) {
            PlaceholderScreen(text = "Client Dashboard")
        }
        composable(Screen.WorkerDashboard.route) {
            PlaceholderScreen(text = "Worker Dashboard")
        }
    }
}

@Composable
fun PlaceholderScreen(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text)
    }
}
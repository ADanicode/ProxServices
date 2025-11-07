package com.example.proxservices.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.proxservices.data.repository.SimulatedAuthRepository
import com.example.proxservices.ui.screen.auth.*
import com.example.proxservices.ui.screen.client.ClientMainScreen
import com.example.proxservices.ui.screen.welcome.WelcomeScreen
import com.example.proxservices.ui.screen.workeri.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    val authRepository = SimulatedAuthRepository()
    val loginViewModelFactory = LoginViewModel.Factory(authRepository)

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        // --- Autenticación ---
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }

        composable(Screen.Login.route) {
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

        // --- Cliente ---
        composable(
            route = Screen.ClientDashboard.route,
            arguments = Screen.ClientDashboard.arguments
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: "error_id_cliente"
            ClientMainScreen(
                mainNavController = navController,
                userId = userId,
                authRepository = authRepository
            )
        }

        // --- Trabajador (workeri) ---
        composable(
            route = Screen.WorkerDashboard.route,
            arguments = Screen.WorkerDashboard.arguments
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: "error_id_trabajador"
            WorkerDashboardScreen(
                navController = navController,
                userId = userId,
                authRepository = authRepository
            )
        }

        // --- Subpantallas del trabajador ---
        composable(Screen.WorkerEditProfile.route) {
            EditProfileScreen(navController = navController)
        }

        composable(Screen.WorkerNotifications.route) {
            WorkerNotificationsScreen(navController = navController)
        }

        composable(
            route = Screen.RateClient.routeWithArgs,
            arguments = Screen.RateClient.arguments
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId") ?: "error_job_id"
            RateClientScreen(navController = navController, jobId = jobId)
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


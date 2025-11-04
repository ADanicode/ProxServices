package com.example.proxservices.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Define todas las rutas y argumentos de la app en un solo lugar.
 */
sealed class Screen(val route: String) {

    // --- Flujo de Autenticación (Rutas Simples) ---
    object Welcome : Screen("welcome_screen")
    object Login : Screen("login_screen")
    object RegisterClient : Screen("register_client_screen")
    object RegisterWorker : Screen("register_worker_screen")
    object ForgotPassword : Screen("forgot_password_screen")

    // --- Flujo Principal del CLIENTE (Contenedor y Pestañas) ---

    // 1. El "Contenedor" Principal (Recibe userId)
    object ClientDashboard : Screen("client_dashboard_screen/{userId}") {
        // La plantilla de la ruta que espera el NavGraph
        val routeWithArgs = "client_dashboard_screen/{userId}"
        // La definición del argumento que espera
        val arguments = listOf(
            navArgument("userId") { type = NavType.StringType }
        )
        // Función para construir la ruta al navegar (ej. "client_dashboard_screen/user-123")
        fun createRoute(userId: String) = "client_dashboard_screen/$userId"
    }

    // 2. Pestañas de la NavBar del Cliente (Rutas Simples Internas)
    object ClientHome : Screen("client_home")
    object ClientSearch : Screen("client_search")
    object ClientServices : Screen("client_services")
    object ClientChatList : Screen("client_chat_list")

    // 3. Sub-pantallas del Cliente (Algunas reciben argumentos)
    object AllCategories : Screen("all_categories_screen")

    object ClientChatDetail : Screen("client_chat_detail/{contactName}") {
        // La plantilla de la ruta
        val routeWithArgs = "client_chat_detail/{contactName}"
        // La definición del argumento
        val arguments = listOf(
            navArgument("contactName") { type = NavType.StringType }
        )
        // Función para construir la ruta (ej. "client_chat_detail/Juan Pérez")
        fun createRoute(contactName: String) = "client_chat_detail/$contactName"
    }

    // --- Flujo Principal del TRABAJADOR (Contenedor y Pestañas) ---

    // 1. El "Contenedor" Principal (Recibe userId)
    object WorkerDashboard : Screen("worker_dashboard_screen/{userId}") {
        // La plantilla de la ruta
        val routeWithArgs = "worker_dashboard_screen/{userId}"
        // La definición del argumento
        val arguments = listOf(
            navArgument("userId") { type = NavType.StringType }
        )
        // Función para construir la ruta
        fun createRoute(userId: String) = "worker_dashboard_screen/$userId"
    }

    // 2. Pestañas de la NavBar del Trabajador (Rutas Simples Internas)
    object WorkerHome : Screen("worker_home")
    object WorkerJobs : Screen("worker_jobs")
    object WorkerWallet : Screen("worker_wallet")
    object WorkerChat : Screen("worker_chat")

    // (Aquí irían futuras sub-pantallas del trabajador)
}


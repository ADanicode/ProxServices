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
    object ClientDashboard : Screen("client_dashboard_screen/{userId}") {
        val routeWithArgs = "client_dashboard_screen/{userId}"
        val arguments = listOf(
            navArgument("userId") { type = NavType.StringType }
        )
        fun createRoute(userId: String) = "client_dashboard_screen/$userId"
    }

    object ClientHome : Screen("client_home")
    object ClientSearch : Screen("client_search")
    object ClientServices : Screen("client_services")
    object ClientChatList : Screen("client_chat_list")

    object AllCategories : Screen("all_categories_screen")

    object ClientChatDetail : Screen("client_chat_detail/{contactName}") {
        val routeWithArgs = "client_chat_detail/{contactName}"
        val arguments = listOf(
            navArgument("contactName") { type = NavType.StringType }
        )
        fun createRoute(contactName: String) = "client_chat_detail/$contactName"
    }

    // --- Flujo Principal del TRABAJADOR (Contenedor y Pestañas) ---
    object WorkerDashboard : Screen("worker_dashboard_screen/{userId}") {
        val routeWithArgs = "worker_dashboard_screen/{userId}"
        val arguments = listOf(
            navArgument("userId") { type = NavType.StringType }
        )
        fun createRoute(userId: String) = "worker_dashboard_screen/$userId"
    }

    object WorkerHome : Screen("worker_home")
    object WorkerJobs : Screen("worker_jobs")
    object WorkerWallet : Screen("worker_wallet")
    object WorkerChat : Screen("worker_chat")

    // --- Sub-pantallas del Trabajador (Ismael) ---
    object WorkerEditProfile : Screen("worker_edit_profile_screen")
    object WorkerNotifications : Screen("worker_notifications_screen")

    object RateClient : Screen("rate_client_screen/{jobId}") {
        val routeWithArgs = "rate_client_screen/{jobId}"
        val arguments = listOf(
            navArgument("jobId") { type = NavType.StringType }
        )
        fun createRoute(jobId: String) = "rate_client_screen/$jobId"
    }

    // --- Sub-pantallas Compartidas (Cliente y Trabajador) ---
    object EditProfile : Screen("edit_profile_screen")
}
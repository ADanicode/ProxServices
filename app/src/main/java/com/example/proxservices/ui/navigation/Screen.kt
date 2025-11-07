package com.example.proxservices.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.NamedNavArgument

/**
 * Define todas las rutas y argumentos de la app en un solo lugar.
 */
sealed class Screen(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {

    // --- Flujo de Autenticación (Rutas Simples) ---
    object Welcome : Screen("welcome_screen")
    object Login : Screen("login_screen")
    object RegisterClient : Screen("register_client_screen")
    object RegisterWorker : Screen("register_worker_screen")
    object ForgotPassword : Screen("forgot_password_screen")

    // --- Flujo Principal del CLIENTE ---

    object ClientDashboard : Screen(
        route = "client_dashboard_screen/{userId}",
        arguments = listOf(navArgument("userId") { type = NavType.StringType })
    ) {
        fun createRoute(userId: String) = "client_dashboard_screen/$userId"
    }

    object ClientHome : Screen("client_home")
    object ClientSearch : Screen("client_search")
    object ClientServices : Screen("client_services")
    object ClientChatList : Screen("client_chat_list")

    object AllCategories : Screen("all_categories_screen")

    object ClientChatDetail : Screen(
        route = "client_chat_detail/{contactName}",
        arguments = listOf(navArgument("contactName") { type = NavType.StringType })
    ) {
        fun createRoute(contactName: String) = "client_chat_detail/$contactName"
    }

    // --- Flujo Principal del TRABAJADOR ---

    object WorkerDashboard : Screen(
        route = "worker_dashboard_screen/{userId}",
        arguments = listOf(navArgument("userId") { type = NavType.StringType })
    ) {
        fun createRoute(userId: String) = "worker_dashboard_screen/$userId"
    }

    object WorkerHome : Screen("worker_home")
    object WorkerJobs : Screen("worker_jobs")
    object WorkerChat : Screen("worker_chat")

    // ⭐ RUTAS AÑADIDAS PARA LA CARTERA Y EL CATÁLOGO ⭐

    // Ruta principal de la Cartera (ya existía como pestaña, pero debe ser un destino completo si no está dentro de WorkerWallet)
    object WorkerWalletDetail : Screen("worker_wallet_detail_screen")

    // 1. Catálogo (Punto de entrada desde WorkerWallet)
    object WorkerCatalog : Screen("worker_catalog_screen")

    // 2. Detalle de Artículo/Canje (Recibe el ID del Item)
    object WorkerRedeemDetail : Screen(
        route = "worker_redeem_detail_screen/{itemId}",
        arguments = listOf(navArgument("itemId") { type = NavType.StringType })
    ) {
        fun createRoute(itemId: String) = "worker_redeem_detail_screen/$itemId"
    }

    // 3. Mock/Ruta de Confirmación de Pago (si la usamos)
    object PaymentConfirmationMock : Screen("payment_confirmation_mock_screen")

    // ⭐ Ruta General WorkerWallet (La que estaba en tu lista original, ahora es el destino base de la pestaña)
    object WorkerWallet : Screen("worker_wallet")
}


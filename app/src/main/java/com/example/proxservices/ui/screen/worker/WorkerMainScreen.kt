package com.example.proxservices.ui.screen.worker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proxservices.data.repository.AuthRepository
import com.example.proxservices.ui.navigation.PlaceholderScreen
import com.example.proxservices.ui.navigation.Screen
import com.example.proxservices.ui.theme.PrimaryCyan
import com.example.proxservices.ui.theme.TextGray

// --- Define los items de la NavBar del Trabajador ---
sealed class WorkerBottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : WorkerBottomBarScreen(
        route = Screen.WorkerHome.route,
        title = "Inicio",
        icon = Icons.Default.Home
    )
    object Jobs : WorkerBottomBarScreen(
        route = Screen.WorkerJobs.route,
        title = "Trabajos",
        icon = Icons.Default.Work
    )
    object Wallet : WorkerBottomBarScreen(
        route = Screen.WorkerWallet.route,
        title = "Billetera",
        icon = Icons.Default.AccountBalanceWallet
    )
    object Chat : WorkerBottomBarScreen(
        route = Screen.WorkerChat.route, // <-- ¡CORREGIDO!
        title = "Chat",
        icon = Icons.AutoMirrored.Filled.Chat
    )
}

/**
 * Pantalla principal que contiene la NavBar del TRABAJADOR
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerMainScreen(
    mainNavController: NavController, // El navegador principal (para cerrar sesión)
    userId: String, // El ID del usuario que se logueó
    authRepository: AuthRepository // El repositorio para pasarlo a los ViewModels
) {
    val internalNavController = rememberNavController() // Navegador solo para las pestañas

    Scaffold(
        bottomBar = {
            WorkerBottomBar(navController = internalNavController) // <-- USA LA NUEVA BARRA M3
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // NavHost interno para las 4 pestañas
            NavHost(
                navController = internalNavController,
                startDestination = WorkerBottomBarScreen.Home.route,
                modifier = Modifier.fillMaxSize()
            ) {
                // --- Pestaña 1: Inicio (donde se muestra el nombre) ---
                composable(route = WorkerBottomBarScreen.Home.route) {
                    // Creamos la Factory para el ViewModel
                    val factory = WorkerHomeViewModel.Factory(
                        userId = userId,
                        authRepository = authRepository
                    )
                    val homeViewModel: WorkerHomeViewModel = viewModel(factory = factory)

                    WorkerHomeScreen(
                        viewModel = homeViewModel
                    )
                }

                // --- Pestaña 2: Trabajos ---
                composable(route = WorkerBottomBarScreen.Jobs.route) {
                    // Placeholder (pantalla por construir)
                    PlaceholderScreen(text = "Pantalla de Trabajos")
                }

                // --- Pestaña 3: Billetera ---
                composable(route = WorkerBottomBarScreen.Wallet.route) {
                    // Placeholder (pantalla por construir)
                    PlaceholderScreen(text = "Pantalla de Billetera")
                }

                // --- Pestaña 4: Lista de Chats ---
                composable(route = WorkerBottomBarScreen.Chat.route) {
                    // Placeholder (pantalla por construir)
                    PlaceholderScreen(text = "Pantalla de Chat del Trabajador")
                }
            }
        }
    }
}


/**
 * Esta es la NavBar de Material 3, corregida.
 */
@Composable
fun WorkerBottomBar(navController: NavHostController) {
    val screens = listOf(
        WorkerBottomBarScreen.Home,
        WorkerBottomBarScreen.Jobs,
        WorkerBottomBarScreen.Wallet,
        WorkerBottomBarScreen.Chat,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar( // <-- ¡COMPONENTE M3 CORREGIDO!
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = TextGray
    ) {
        screens.forEach { screen ->
            NavigationBarItem( // <-- ¡COMPONENTE M3 CORREGIDO!
                label = {
                    Text(text = screen.title, fontSize = 9.sp)
                },
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = screen.title)
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = TextGray,
                    unselectedTextColor = TextGray,
                    selectedIconColor = PrimaryCyan, // <-- Color de tu Figma
                    selectedTextColor = PrimaryCyan, // <-- Color de tu Figma
                    indicatorColor = MaterialTheme.colorScheme.background // Sin indicador
                )
            )
        }
    }
}


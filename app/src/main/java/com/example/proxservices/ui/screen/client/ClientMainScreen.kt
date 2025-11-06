package com.example.proxservices.ui.screen.client

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
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
import com.example.proto.ui.screen.jobs.BusquedaScreen
import com.example.proxservices.data.repository.AuthRepository
import com.example.proxservices.ui.navigation.Screen
import com.example.proxservices.ui.theme.PrimaryCyan
import com.example.proxservices.ui.theme.TextBlack
import com.example.proxservices.ui.theme.TextGray

// --- Define los items de la NavBar del Cliente ---
sealed class ClientBottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : ClientBottomBarScreen(
        route = Screen.ClientHome.route,
        title = "Inicio",
        icon = Icons.Default.Home
    )
    object Search : ClientBottomBarScreen(
        route = Screen.ClientSearch.route,
        title = "Buscar",
        icon = Icons.Default.Search
    )
    object Services : ClientBottomBarScreen(
        route = Screen.ClientServices.route,
        title = "Servicios",
        icon = Icons.Default.Work
    )
    object Chat : ClientBottomBarScreen(
        // ¡¡CORRECCIÓN 1!!
        route = Screen.ClientChatList.route, // <-- Debe ser ClientChatList
        title = "Chat",
        icon = Icons.AutoMirrored.Filled.Chat
    )
}

/**
 * Pantalla principal que contiene la NavBar del Cliente y el NavHost interno
 * para las 4 pestañas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientMainScreen(
    mainNavController: NavController, // El navegador principal (para cerrar sesión)
    userId: String, // El ID del usuario que se logueó
    authRepository: AuthRepository // El repositorio para pasarlo a los ViewModels
) {
    val internalNavController = rememberNavController() // Navegador solo para las pestañas

    Scaffold(
        bottomBar = {
            ClientBottomBar(navController = internalNavController) // <-- USA LA NUEVA BARRA M3
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // NavHost interno para las 4 pestañas
            NavHost(
                navController = internalNavController,
                startDestination = ClientBottomBarScreen.Home.route,
                modifier = Modifier.fillMaxSize()
            ) {
                // --- Pestaña 1: Inicio (donde se muestra el nombre) ---
                composable(route = ClientBottomBarScreen.Home.route) {
                    // Creamos la Factory para el ViewModel
                    val factory = ClientHomeViewModel.Factory(
                        userId = userId,
                        authRepository = authRepository
                    )
                    val homeViewModel: ClientHomeViewModel = viewModel(factory = factory)

                    ClientHomeScreen(
                        // ¡¡CORRECCIÓN 2!!
                        navController = internalNavController, // <-- Pasa el NavController
                        viewModel = homeViewModel,
                        onNavigateToAllCategories = {
                            // Navegación a la sub-pantalla de "Todas las Categorías"
                            internalNavController.navigate(route = Screen.AllCategories.route)
                        }
                    )
                }

                // --- Pestaña 2: Buscar ---
                composable(route = ClientBottomBarScreen.Search.route) {
                    BusquedaScreen(navController = internalNavController)
                }

                // --- Pestaña 3: Servicios ---
                composable(route = ClientBottomBarScreen.Services.route) {
                    ServicesSceen(navController = internalNavController)
                }

                // --- Pestaña 4: Lista de Chats ---
                composable(route = ClientBottomBarScreen.Chat.route) {
                    ChatListScreen(
                        navController = internalNavController,
                        onNavigateToChatDetail = { contactName ->
                            // Navegación a la sub-pantalla de "Chat Detalle"
                            internalNavController.navigate(
                                Screen.ClientChatDetail.createRoute(contactName)
                            )
                        }
                    )
                }

                // --- Sub-pantallas (no están en la NavBar) ---
                composable(route = Screen.AllCategories.route) {
                    AllCategoriesScreen(navController = internalNavController)
                }

                composable(
                    route = Screen.ClientChatDetail.route,
                    arguments = Screen.ClientChatDetail.arguments
                ) { backStackEntry ->
                    val contactName = backStackEntry.arguments?.getString("contactName") ?: "Error"
                    ChatDetailScreen(
                        navController = internalNavController,
                        contactName = contactName
                    )
                }
            }
        }
    }
}


/**
 * Esta es la NavBar de Material 3, corregida.
 */
@Composable
fun ClientBottomBar(navController: NavHostController) {
    val screens = listOf(
        ClientBottomBarScreen.Home,
        ClientBottomBarScreen.Search,
        ClientBottomBarScreen.Services,
        ClientBottomBarScreen.Chat,
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
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // re-selecting the same item
                        launchSingleTop = true
                        // Restore state when re-selecting a previously selected item
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


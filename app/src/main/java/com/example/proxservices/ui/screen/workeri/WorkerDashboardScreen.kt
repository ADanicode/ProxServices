package com.example.proxservices.ui.screen.workeri

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proxservices.ui.navigation.Screen
import com.example.proxservices.ui.theme.BackgroundLight
import com.example.proxservices.ui.theme.CardBorder
import com.example.proxservices.ui.theme.CardBorderStroke
import com.example.proxservices.ui.theme.PrimaryCyan
import com.example.proxservices.ui.theme.PrimaryPurple
import com.example.proxservices.ui.theme.StarYellow
import com.example.proxservices.ui.theme.TextBlack
import com.example.proxservices.ui.theme.TextGray

@Composable
fun WorkerDashboardScreen(
    navController: NavController,
    viewModel: WorkerViewModel = viewModel()
) {
    // Observa el estado de disponibilidad del ViewModel
    val isAvailable by viewModel.isAvailable.observeAsState(initial = true)
    val profile by viewModel.profile.observeAsState(initial = WorkerProfile())

    Scaffold(
        containerColor = BackgroundLight,
        bottomBar = { WorkerBottomNavigationBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item { HeaderSection(profile = profile, viewModel = viewModel, isAvailable = isAvailable) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { WalletCard(balance = profile.walletBalance) { viewModel.viewWalletHistory() } }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { ReputationSection(rating = profile.reputation, reviews = profile.totalReviews) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { QuickAccessSection(navController = navController) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { RecentActivitySection() }
            item { Spacer(modifier = Modifier.height(32.dp)) } // Espacio final
        }
    }
}

// --- Componentes Reutilizables ---

@Composable
fun HeaderSection(
    profile: WorkerProfile,
    viewModel: WorkerViewModel,
    isAvailable: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Simulación del Avatar
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(PrimaryCyan.copy(alpha = 0.2f))
        ) {
            Icon(
                Icons.Filled.Person,
                contentDescription = "Avatar de Trabajador",
                tint = PrimaryCyan,
                modifier = Modifier.align(Alignment.Center).size(40.dp)
            )
        }
        Spacer(Modifier.width(12.dp))

        // Info de Perfil
        Column(Modifier.weight(1f)) {
            Text(
                text = profile.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextBlack
            )
            Text(
                text = profile.title,
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray
            )
        }

        // Toggle de Disponibilidad
        Switch(
            checked = isAvailable,
            onCheckedChange = { viewModel.toggleAvailability(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = PrimaryCyan,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = TextGray
            )
        )
    }

    // Etiqueta de Estado
    Row(
        modifier = Modifier.padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Estado:",
            style = MaterialTheme.typography.bodyMedium,
            color = TextBlack
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = if (isAvailable) "Disponible" else "No Disponible",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = if (isAvailable) PrimaryCyan else TextGray
        )
    }
}

@Composable
fun WalletCard(balance: Double, onHistoryClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryPurple.copy(alpha = 0.1f)),
        border = CardBorderStroke,
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Billetera",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryPurple
                )
                // Icono simulando el estado/conexión
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(PrimaryCyan)
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = "$${String.format("%.2f", balance)}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = TextBlack
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onHistoryClick,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Ver Historial / Retirar Fondos")
            }
        }
    }
}

@Composable
fun ReputationSection(rating: Double, reviews: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Reputación",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = TextBlack
        )
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Estrellas
            repeat(5) { index ->
                Icon(
                    Icons.Filled.Star,
                    contentDescription = null,
                    tint = if (index < rating.toInt()) StarYellow else TextGray,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = String.format("%.1f", rating),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = StarYellow
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "($reviews reseñas totales)",
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray
            )
        }
    }
}

@Composable
fun QuickAccessSection(navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Accesos Rápidos",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = TextBlack
        )
        Spacer(Modifier.height(12.dp))
        QuickAccessItem(
            icon = Icons.Filled.Person,
            title = "Editar Perfil",
            subtitle = "Actualizar información y portafolio",
            onClick = { navController.navigate(Screen.EditProfile.route) } // Conexión a EditProfileScreen
        )
        Divider(color = CardBorder, thickness = 1.dp, modifier = Modifier.padding(horizontal = 4.dp))
        QuickAccessItem(
            icon = Icons.Filled.Task,
            title = "Servicios Pendientes",
            subtitle = "3 solicitudes por revisar",
            onClick = { /* Navegar a Solicitudes Pendientes */ }
        )
    }
}

@Composable
fun QuickAccessItem(icon: ImageVector, title: String, subtitle: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = PrimaryCyan,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = TextBlack
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextGray
            )
        }
        Icon(
            Icons.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = TextGray,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun RecentActivitySection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Actividad Reciente",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = TextBlack
        )
        Spacer(Modifier.height(12.dp))
        // Ejemplo de item de actividad
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Nueva Reseña",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = TextBlack
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Carlos Mendoza te calificó",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextGray
                    )
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        tint = StarYellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "5.0",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = StarYellow
                    )
                }
                Text(
                    text = "Hace 1 día",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextGray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}


// Items para la barra de navegación inferior
sealed class WorkerNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object Home : WorkerNavItem("worker_home", Icons.Filled.Home, "Inicio")
    data object Jobs : WorkerNavItem("worker_jobs", Icons.Filled.Task, "Trabajos")
    data object Wallet : WorkerNavItem("worker_wallet", Icons.Filled.Wallet, "Billetera")
    data object Chat : WorkerNavItem("worker_chat", Icons.Filled.Message, "Chat")
}

@Composable
fun WorkerBottomNavigationBar(navController: NavController) {
    val items = listOf(
        WorkerNavItem.Home,
        WorkerNavItem.Jobs,
        WorkerNavItem.Wallet,
        WorkerNavItem.Chat
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = PrimaryCyan,
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = item.route == "worker_home", // Temporalmente siempre 'home' para el Dashboard
                onClick = {
                    // Aquí se implementaría la lógica de navegación real
                    // Por ahora, solo imprime la acción
                    println("Navegando a: ${item.label}")
                    // navController.navigate(item.route) // Descomentar para navegación real
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryCyan,
                    selectedTextColor = PrimaryCyan,
                    unselectedIconColor = TextGray,
                    unselectedTextColor = TextGray
                )
            )
        }
    }
}
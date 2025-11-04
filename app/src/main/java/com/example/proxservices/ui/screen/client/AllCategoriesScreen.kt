package com.example.proxservices.ui.screen.client

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MoveDown
import androidx.compose.material.icons.filled.Plumbing
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Wash
import androidx.compose.material.icons.filled.Yard
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proxservices.ui.navigation.Screen
import com.example.proxservices.ui.theme.BackgroundLight
import com.example.proxservices.ui.theme.PrimaryCyan
import com.example.proxservices.ui.theme.TextFieldBackground

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AllCategoriesScreen(
    navController: NavController // El NavController interno del ClientMainScreen
) {
    // Lista COMPLETA simulada de categorías
    val allCategories = listOf(
        "Plomería" to Icons.Default.Plumbing,
        "Electricidad" to Icons.Default.ElectricBolt,
        "Jardinería" to Icons.Default.Yard,
        "Pintura" to Icons.Default.FormatPaint,
        "Reparación" to Icons.Default.HomeRepairService,
        "Mudanza" to Icons.Default.MoveDown,
        "Limpieza" to Icons.Default.Wash,
        "Carpintería" to Icons.Default.HomeRepairService,
        "Cerrajería" to Icons.Default.LockOpen,
        "Mecánica" to Icons.Default.Search, // Placeholder
        "Enseñanza" to Icons.Default.School,
        "Belleza" to Icons.Default.Spa,
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todas las Categorías", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    // Botón para volver a la pantalla anterior (ClientHomeScreen)
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()) // Permite scroll si hay muchas categorías
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Cuadrícula FlowRow que muestra todas las categorías
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = 4, // 4 items por fila
                // Espaciado horizontal se ajusta automáticamente
                horizontalArrangement = Arrangement.SpaceBetween,
                // Espaciado vertical entre filas
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                allCategories.forEach { (nombre, icono) ->
                    CategoryChip(text = nombre, icon = icono, onClick = {
                        // Al hacer clic, vuelve a la pestaña "Buscar" (simulación)
                        navController.navigate(Screen.ClientSearch.route) {
                            // Limpia la pila hasta la pantalla de inicio del cliente
                            popUpTo(Screen.ClientHome.route)
                        }
                    })
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// Componente reutilizado de ClientHomeScreen
// (Definido aquí como privado para mantener el archivo autocontenido)
@Composable
private fun CategoryChip(text: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(72.dp) // Ancho fijo para 4 en una fila
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(TextFieldBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = text, tint = PrimaryCyan)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, style = MaterialTheme.typography.labelMedium, textAlign = TextAlign.Center)
    }
}


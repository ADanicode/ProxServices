package com.example.proxservices.ui.screen.client

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource // <-- IMPORTACIÓN NECESARIA
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember // <-- IMPORTACIÓN NECESARIA
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proxservices.R
import com.example.proxservices.ui.theme.*

// --- CAMBIO 1: Modelo de datos SIN icono ---
data class Category(val name: String)

// --- CAMBIO 2: Lista SIN icono ---
val categoryList = listOf(
    Category("Plomería"),
    Category("Electricidad"),
    Category("Carpintería"),
    Category("Jardinería"),
    Category("Limpieza"),
    Category("Pintura"),
    Category("Mudanzas"),
    Category("Mecánica"),
    Category("Informática")
)

/**
 * Esta es la pantalla de "Inicio" (la primera pestaña) del Cliente.
 * Muestra el nombre del usuario y las categorías.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ClientHomeScreen(
    navController: NavController,
    viewModel: ClientHomeViewModel,
    onNavigateToAllCategories: () -> Unit
) {
    // --- ¡¡INICIO DE LA PRUEBA DE CRASH!! ---
    // Hemos deshabilitado todo el LazyColumn.
    // Si la app se abre y ves este mensaje, significa que el crash
    // está 100% dentro del LazyColumn (probablemente un clickable).
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Client Home Screen ¡SÍ FUNCIONA!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
    // --- ¡¡FIN DE LA PRUEBA DE CRASH!! ---


    /*
    // --- CÓDIGO ANTIGUO (TEMPORALMENTE DESHABILITADO) ---
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        // ... todo el código que crashea ...
    }
    */
}

/**
 * "Chip" individual para cada categoría en la cuadrícula
 */
@Composable
fun CategoryChip(category: Category, onClick: () -> Unit, modifier: Modifier = Modifier) {
    // ... (Este código no se está usando ahora mismo)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable( // <-- MANTENEMOS EL ARREGLO DEL CRASH
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(8.dp)
    ) {
        // --- CAMBIO 3: Icono eliminado ---
        // El Box y el Icon fueron eliminados
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = category.name,
            fontSize = 13.sp,
            color = TextBlack,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * Tarjeta para mostrar un servicio/trabajador en la lista
 */
@Composable
fun WorkerServiceCard(
    serviceName: String,
    workerName: String,
    rating: Double,
    onClick: () -> Unit
) {
    // ... (Este código no se está usando ahora mismo)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clickable( // <-- MANTENEMOS EL ARREGLO DEL CRASH
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher),
                contentDescription = workerName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = serviceName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextBlack
                )
                Text(
                    text = workerName,
                    fontSize = 14.sp,
                    color = TextGray
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Ver detalle",
                tint = TextGray
            )
        }
    }
}


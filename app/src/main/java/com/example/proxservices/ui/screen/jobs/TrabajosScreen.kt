package com.example.proto.ui.screen.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// Color celeste principal
private val Celeste = Color(0xFF00BCD4)

@Composable
fun TrabajosScreen(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }
    var reviewServicio by remember { mutableStateOf<Servicio?>(null) } // para abrir pantalla de reseña
    var showBusqueda by remember { mutableStateOf(false) }
    var showFiltros by remember { mutableStateOf(false) }
    if (showFiltros) {
        // 🔹 Muestra la pantalla de filtros
        FiltrosScreen(
            onVolverBusqueda = { showFiltros = false },
            onAplicarFiltros = { showFiltros = false }
        )}
    else if (showBusqueda) {
        // 🔹 Muestra la pantalla de búsqueda cuando se da clic
        BusquedaScreen(
            onContactarClick = {
                showBusqueda = false
                showFiltros = true
            },
            navController = TODO(),
            onBackClick = TODO(),
            onFiltroClick = TODO()
        )
    } else {

        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(modifier = Modifier.height(10.dp))
            // --- Header superior (lo que mencionaste que va encima de ProxService)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 🔹 Icono de perfil circular (izquierda)
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFB2EBF2)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Perfil",
                        tint = Celeste,
                        modifier = Modifier.size(26.dp)
                    )
                }

                // 🔹 Título centrado
                Text(
                    text = "ProxService",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Celeste,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                )

                // 🔹 Icono de búsqueda (derecha)
                IconButton(
                    onClick = { showBusqueda = true },
                    modifier = Modifier.size(32.dp)
                ){Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = Celeste
                )
            }
            }

            // --- Subtítulo
            Text(
                "Mis servicios contratados",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            // --- Tabs (Completados / En Progreso / Pendientes)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TabButton("Completados", selectedIndex == 0, Celeste) { selectedIndex = 0 }
                TabButton("En Progreso", selectedIndex == 1, Celeste) { selectedIndex = 1 }
                TabButton("Pendientes", selectedIndex == 2, Celeste) { selectedIndex = 2 }
                TabButton("Ayuda", selectedIndex == 3, Celeste) { selectedIndex = 3 }
            }

            Divider()

            // --- Contenido dinámico: le pasamos callback para abrir reseña desde Completados
            ContentScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                selectedIndex = selectedIndex,
                onRequestReview = { servicio -> reviewServicio = servicio }
            )
        }

        // Si reviewServicio != null mostramos ReviewScreen encima
        reviewServicio?.let { servicio ->
            // Pantalla modal simple de reseña — ReviewScreen tiene onClose
            ReviewScreen(
                servicio = servicio,
                onClose = { reviewServicio = null },
                onSubmit = { rating, comentario ->
                    // aquí puedes guardar la reseña (API/DB). Por ahora solo cerramos.
                    reviewServicio = null
                }
            )
        }
    }
}




@Composable
fun TabButton(title: String, selected: Boolean, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) color else MaterialTheme.colorScheme.surface,
            contentColor = if (selected) Color.White else MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.height(40.dp)
    ) {
        Text(text = title)
    }
}


@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onRequestReview: (Servicio) -> Unit
) {
    when (selectedIndex) {
        0 -> CompletadosScreen(
            modifier = modifier, onReviewClick = onRequestReview,
            navController = TODO()
        )
        1 -> EnProgresoScreen(
            modifier = modifier,
            navController = TODO()
        )
        2 -> PendientesScreen(modifier = modifier,navController = rememberNavController())
        3 -> AyudaYDisputasScreen(navController = rememberNavController())
    }
}
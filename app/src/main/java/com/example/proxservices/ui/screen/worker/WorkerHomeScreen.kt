package com.example.proxservices.ui.screen.worker

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
import androidx.compose.material.icons.filled.Info
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
import androidx.navigation.NavController
import com.example.proxservices.R
import com.example.proxservices.ui.theme.*

// Modelo de datos simulado para un trabajo
data class JobInProgress(
    val id: String,
    val title: String,
    val clientName: String,
    val status: String,
    val imageUrl: Int // Usamos Int para R.mipmap.ic_launcher
)

// Lista simulada de trabajos
val jobList = listOf(
    JobInProgress("1", "Reparación de Tubería", "Juan Pérez", "En Progreso", R.mipmap.ic_launcher),
    JobInProgress("2", "Instalación Eléctrica", "Ana García", "Pendiente", R.mipmap.ic_launcher),
    JobInProgress("3", "Pintura de Habitación", "Luis Torres", "En Progreso", R.mipmap.ic_launcher)
)

/**
 * Esta es la pantalla de "Inicio" (la primera pestaña) del Trabajador.
 * Muestra el nombre y la lista de trabajos.
 */
@Composable
fun WorkerHomeScreen(
    viewModel: WorkerHomeViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        // --- Saludo al Usuario ---
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(
                    text = if (uiState.isLoading) "Cargando..." else "Hola,",
                    fontSize = 22.sp,
                    color = TextGray
                )
                Text(
                    text = uiState.userName, // <-- Muestra el nombre
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextBlack
                )
            }
        }

        // --- Título de Trabajos ---
        item {
            Text(
                text = "Mis Trabajos en Progreso",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextBlack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp, bottom = 16.dp)
            )
        }

        // --- Lista de Trabajos ---
        if (uiState.isLoading) {
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        } else {
            items(jobList) { job ->
                JobInProgressCard(
                    job = job,
                    onClick = {
                        // TODO: Navegar al detalle del trabajo
                    }
                )
            }
        }

        // Espacio al final
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

/**
 * Tarjeta para mostrar un trabajo en progreso (basado en Figma)
 */
@Composable
fun JobInProgressCard(job: JobInProgress, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clickable( // <-- ¡¡AQUÍ ESTÁ LA CORRECCIÓN!!
                interactionSource = remember { MutableInteractionSource() },
                indication = null, // Se quita el "ripple" de M2
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
                painter = painterResource(id = job.imageUrl),
                contentDescription = job.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = job.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextBlack
                )
                Text(
                    text = "Cliente: ${job.clientName}",
                    fontSize = 14.sp,
                    color = TextGray
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Estado",
                        tint = TagBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = job.status,
                        fontSize = 14.sp,
                        color = TagBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
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


package com.example.proxservices.ui.screen.client

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.proxservices.R // Necesitarás una imagen placeholder
import com.example.proxservices.ui.theme.*

@Composable
fun ServicesScreen(
    navController: NavController
) {
    // Lista simulada de servicios contratados
    val hiredServices = listOf(
        ServiceItem("Reparación de Tubería", "Juan Pérez (Plomero)", "En Progreso", "En 2 días", 500.00),
        ServiceItem("Instalación Eléctrica", "María González (Electricista)", "Pendiente", "Para el 10/Dic", 1200.00),
        ServiceItem("Mantenimiento de Jardín", "Carlos Ruiz (Jardinero)", "En Progreso", "Hoy a las 4 PM", 400.00)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .padding(vertical = 16.dp) // Añadido padding vertical
    ) {
        Text(
            text = "Mis Servicios Contratados",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextBlack,
            modifier = Modifier.padding(horizontal = 24.dp) // Padding horizontal
        )
        Spacer(modifier = Modifier.height(24.dp))

        // --- Lista de Servicios ---
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
        ) {
            items(hiredServices.size) { index ->
                val service = hiredServices[index]
                ServiceItemCard(
                    title = service.title,
                    workerName = service.workerName,
                    status = service.status,
                    dateInfo = service.dateInfo,
                    price = service.price
                )
            }
        }
    }
}

// Data class simulada para un servicio contratado
private data class ServiceItem(
    val title: String,
    val workerName: String,
    val status: String,
    val dateInfo: String,
    val price: Double
)

// Composable para la tarjeta de servicio
@Composable
private fun ServiceItemCard(
    title: String,
    workerName: String,
    status: String,
    dateInfo: String,
    price: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent), // Sin fondo
        border = BorderStroke(1.dp, CardBorder), // Borde sutil
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Título y Nombre del Trabajador
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = TextBlack
                    )
                    Text(
                        text = "con $workerName",
                        fontSize = 14.sp,
                        color = TextGray
                    )
                }
                // Menú (simulado)
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "Opciones",
                    tint = TextGray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Detalles del Servicio ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Status
                Text(
                    text = status,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = if (status == "En Progreso") TagBlue else PrimaryCyan,
                    modifier = Modifier
                        .background(
                            (if (status == "En Progreso") TagBlue else PrimaryCyan).copy(alpha = 0.1f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )

                // Precio
                Text(
                    text = "$${String.format("%.2f", price)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextBlack
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = CardBorder)
            Spacer(modifier = Modifier.height(16.dp))

            // --- Fecha e Imagen ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = dateInfo,
                    fontSize = 14.sp,
                    color = TextGray
                )
                // Imagen de perfil simulada
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // Placeholder
                    contentDescription = "Foto de $workerName",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(TextGray.copy(alpha = 0.2f))
                )
            }
        }
    }
}


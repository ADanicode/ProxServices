package com.example.proxservices.ui.screen.workeri

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proxservices.ui.theme.BackgroundLight
import com.example.proxservices.ui.theme.PrimaryCyan
import com.example.proxservices.ui.theme.StarYellow

// Data class para simular una notificación
sealed class NotificationType {
    data class Review(val sender: String, val rating: Float, val comment: String) : NotificationType()
    data class NewRequest(val sender: String) : NotificationType()
    data class Payment(val amount: Float) : NotificationType()
}

data class Notification(
    val id: Int,
    val type: NotificationType,
    val timeAgo: String,
    val isRead: Boolean = false
)

// Datos de ejemplo
val sampleNotifications = listOf(
    Notification(1, NotificationType.Review("Maria González", 5.0f, "Excelente trabajo, muy profesional y puntual. La casa quedó impecable y superó mis expectativas."), "Hace 2 min"),
    Notification(2, NotificationType.Review("Carlos Rodríguez", 4.0f, "Buen servicio, llegó a tiempo y completó todo el trabajo solicitado."), "Hace 1 hora"),
    Notification(3, NotificationType.NewRequest("Ana López"), "Hace 3 horas"),
    Notification(4, NotificationType.Review("Roberto Silva", 5.0f, "Increíble atención al detalle. Definitivamente lo recomendaré a otros."), "Hace 1 día"),
    Notification(5, NotificationType.Payment(75.00f), "Hace 2 días")
)

@Composable
fun WorkerNotificationsScreen(navController: NavController) {
    Scaffold(
        containerColor = BackgroundLight,
        topBar = { NotificationsTopBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(sampleNotifications) { notification ->
                NotificationItem(notification = notification) {
                    // Acción al clickear el botón
                }
            }
        }
    }
}

// --- Componentes Reutilizables ---

@Composable
fun NotificationsTopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
        }
        Text(
            text = "Notificaciones",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { /* Acción para más opciones */ }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Más Opciones")
        }
    }
}

@Composable
fun NotificationItem(notification: Notification, onActionClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                // Icono/Iniciales
                val (initials, color) = when (notification.type) {
                    is NotificationType.Review -> Pair(notification.type.sender.take(2), PrimaryCyan)
                    is NotificationType.NewRequest -> Pair(notification.type.sender.take(2), Color.Gray)
                    is NotificationType.Payment -> Pair("$", Color.Green)
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = initials,
                        color = color,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Contenido
                Column(modifier = Modifier.weight(1f)) {
                    val title = when (notification.type) {
                        is NotificationType.Review -> "¡Nueva Reseña de ${notification.type.sender}!"
                        is NotificationType.NewRequest -> "${notification.type.sender} te ha enviado una nueva solicitud de servicio"
                        is NotificationType.Payment -> "Pago Recibido"
                    }
                    Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

                    when (notification.type) {
                        is NotificationType.Review -> {
                            // Estrellas y Comentario
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                repeat(5) { index ->
                                    Icon(
                                        imageVector = if (index < notification.type.rating) Icons.Default.Star else Icons.Default.StarOutline,
                                        contentDescription = null,
                                        tint = StarYellow,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("(${String.format("%.1f", notification.type.rating)})", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            }
                            Text(
                                text = notification.type.comment.take(80) + if (notification.type.comment.length > 80) "..." else "",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        is NotificationType.Payment -> {
                            Text(
                                text = "Pago de $${String.format("%.2f", notification.type.amount)} procesado exitosamente",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        else -> { /* No hay contenido extra para solicitud */ }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(notification.timeAgo, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }

            // Botón de Acción
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(
                    onClick = onActionClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan)
                ) {
                    val buttonText = when (notification.type) {
                        is NotificationType.Review -> "Ver Reseña Completa"
                        is NotificationType.NewRequest -> "Ver Solicitud"
                        is NotificationType.Payment -> "Ver Detalles"
                    }
                    Text(buttonText, style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}
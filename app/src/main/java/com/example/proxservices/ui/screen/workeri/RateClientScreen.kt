package com.example.proxservices.ui.screen.workeri

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.proxservices.ui.theme.TextFieldBackground
import com.example.proxservices.ui.theme.TextGray // Usar el color gris para texto secundario

@Composable
fun RateClientScreen(
    navController: NavController,
    jobId: String // Se obtiene de la ruta de navegación
) {
    var rating by remember { mutableStateOf(3) } // Estado de calificación (1 a 5)
    var reviewText by remember { mutableStateOf("") } // Estado del comentario
    val clientName = "Maria González" // Ejemplo, idealmente viene de un ViewModel

    Scaffold(
        topBar = { RateClientTopBar(navController, clientName) },
        containerColor = BackgroundLight
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Título y Pregunta Principal
                Text(
                    text = "Calificar Cliente",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "¿Cómo fue trabajar con $clientName?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextGray,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Sección de Calificación Bidireccional (Texto informativo)
                BidirectionalRatingInfo()
                Spacer(modifier = Modifier.height(24.dp))

                // Selector de Calificación (Estrellas)
                RatingSelector(rating = rating, onRatingChange = { rating = it })
                Spacer(modifier = Modifier.height(16.dp))

                // Reseña Escrita (Opcional)
                ReviewTextField(reviewText = reviewText, onTextChange = { reviewText = it })
                Spacer(modifier = Modifier.height(24.dp))

                // Botón Enviar Calificación
                Button(
                    onClick = { /* Lógica de envío de calificación al backend */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan),
                    contentPadding = PaddingValues(vertical = 14.dp)
                ) {
                    Text("Enviar Calificación", fontWeight = FontWeight.Bold)
                }

                // Reportar Cliente
                Text(
                    text = "¿Tuviste algún problema con este cliente?",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextGray,
                    modifier = Modifier.padding(top = 16.dp)
                )
                TextButton(onClick = { /* Navegar a la pantalla de Reporte */ }) {
                    Text("Reportar Cliente", color = Color.Red, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(32.dp))

                // Información Importante
                ImportantInfoSection()
            }
        }
    }
}

// =========================================================================
//                             COMPONENTES REUTILIZABLES
// =========================================================================

@Composable
fun RateClientTopBar(navController: NavController, clientName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 10.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
            }
            Text(
                text = "Calificar $clientName",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        // Icono de reloj (historial de calificación simulado)
        Icon(Icons.Default.Schedule, contentDescription = "Historial", tint = TextGray)
    }
    Divider(color = Color.LightGray.copy(alpha = 0.5f))
}

@Composable
fun BidirectionalRatingInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(PrimaryCyan.copy(alpha = 0.1f))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CheckCircle, contentDescription = "Calificación Bidireccional", tint = PrimaryCyan, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Calificación Bidireccional", fontWeight = FontWeight.SemiBold)
        }
        Text(
            text = "Tu calificación ayuda a mantener la calidad del servicio y fomenta el respeto mutuo en la plataforma.",
            style = MaterialTheme.typography.bodySmall,
            color = TextGray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun RatingSelector(rating: Int, onRatingChange: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Calificación *", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        Text(
            text = "Selecciona de 1 a 5 estrellas para calificar tu experiencia trabajando con este cliente.",
            style = MaterialTheme.typography.bodySmall,
            color = TextGray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(5) { index ->
                Icon(
                    imageVector = if (index < rating) Icons.Default.Star else Icons.Default.StarOutline,
                    contentDescription = "Estrella ${index + 1}",
                    tint = StarYellow,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onRatingChange(index + 1) }
                )
            }
        }
    }
}

@Composable
fun ReviewTextField(reviewText: String, onTextChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Reseña Escrita (Opcional)", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)

        OutlinedTextField(
            value = reviewText,
            onValueChange = onTextChange,
            placeholder = { Text("Tu reseña será visible para otros trabajadores y ayudará a la comunidad.") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = false,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = TextFieldBackground,
                focusedContainerColor = TextFieldBackground,
                focusedBorderColor = PrimaryCyan,
                unfocusedBorderColor = Color.Transparent
            )
        )
    }
}

@Composable
fun ImportantInfoSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Info, contentDescription = "Información", tint = TextGray, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Información Importante", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
        }
        Text(
            text = "• Las calificaciones son permanentes y no se pueden editar después de ser enviadas.",
            style = MaterialTheme.typography.bodySmall,
            color = TextGray,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}
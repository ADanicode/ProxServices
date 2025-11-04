package com.example.proxservices.ui.screen.client

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proxservices.R // Necesitarás una imagen de perfil de trabajador
import com.example.proxservices.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController
) {
    var searchQuery by remember { mutableStateOf("") }

    // Lista simulada de trabajadores
    val searchResults = listOf(
        WorkerResult("Juan Pérez", "Plomero", 4.8, 120, 500.00),
        WorkerResult("María González", "Electricista", 4.9, 85, 650.00),
        WorkerResult("Carlos Ruiz", "Jardinero", 4.7, 95, 400.00),
        WorkerResult("Ana Torres", "Pintora", 4.8, 110, 550.00)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Búsqueda de Servicios",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextBlack
        )
        Spacer(modifier = Modifier.height(16.dp))

        // --- Barra de Búsqueda ---
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Busca un Plomero, Electricista...", color = TextGray) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Buscar", tint = TextGray)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = CardBorder,
                focusedBorderColor = PrimaryCyan,
                containerColor = TextFieldBackground
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Resultados Populares",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = TextBlack
        )
        Spacer(modifier = Modifier.height(16.dp))

        // --- Lista de Resultados ---
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(searchResults.size) { index ->
                val result = searchResults[index]
                WorkerResultCard(
                    name = result.name,
                    job = result.job,
                    rating = result.rating,
                    reviewCount = result.reviewCount,
                    price = result.price
                )
            }
        }
    }
}

// Data class simulada para un resultado
private data class WorkerResult(
    val name: String,
    val job: String,
    val rating: Double,
    val reviewCount: Int,
    val price: Double
)

// Composable para la tarjeta de resultado
@Composable
private fun WorkerResultCard(
    name: String,
    job: String,
    rating: Double,
    reviewCount: Int,
    price: Double
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CardBorder, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundLight),
        onClick = { /* TODO: Navegar al perfil del trabajador */ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen de perfil simulada
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Placeholder
                contentDescription = "Foto de $name",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(TextGray.copy(alpha = 0.2f))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Información del trabajador
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextBlack
                )
                Text(
                    text = job,
                    fontSize = 14.sp,
                    color = PrimaryCyan
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107), // Amarillo estrella
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$rating ($reviewCount reseñas)",
                        fontSize = 14.sp,
                        color = TextGray
                    )
                }
            }

            // Precio
            Text(
                text = "$${String.format("%.2f", price)}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextBlack
            )
        }
    }
}


package com.example.proto.ui.screen.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private val Celeste = Color(0xFF00BCD4)

private val serviciosCompletados = listOf(
    Servicio("1","María González", "Limpieza del Hogar", "15 Mar 2024", "$75", "Completado", 4.8),
    Servicio("2","Carlos Mendoza", "Plomería", "12 Mar 2024", "$120", "Completado", null),
    Servicio("3","Sofía Martín", "Carpintería", "05 Mar 2024", "$200", "Completado", 4.9)
)

@Composable
fun CompletadosScreen(modifier: Modifier = Modifier, onReviewClick: (Servicio) -> Unit, navController: NavController)  {
    Column(modifier = modifier) {
        Text(text = "Completado", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(vertical = 8.dp))

        // Lista simple (puedes cambiar a LazyColumn si quieres muchas tarjetas)
        Column {
            serviciosCompletados.forEach { s ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        AvatarInitials(s.nombre)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(s.nombre, style = MaterialTheme.typography.titleMedium)
                            Text(s.oficio, color = Color.Gray)
                            Text("Finalizado: ${s.fecha}", color = Color.Gray, fontSize = 12.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(s.estado, color = Celeste, style = MaterialTheme.typography.bodySmall)
                            Text(s.precio, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(6.dp))
                            if (s.rating != null) {
                                Text("⭐ ${s.rating}", color = Color(0xFFFFB300))
                            } else {
                                // Botón dejar reseña
                                Button(
                                    onClick = { onReviewClick(s) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Celeste),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text("Dejar Reseña", color = Color.White, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AvatarInitials(nombre: String) {
    val initials = nombre.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString("")
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(Celeste, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(text = initials, color = Color.White, style = MaterialTheme.typography.titleSmall)
    }
}

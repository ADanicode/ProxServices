package com.example.proto.ui.screen.jobs


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReviewScreen(
    servicio: Servicio,
    onClose: () -> Unit,
    onSubmit: (rating: Int, comentario: String) -> Unit
) {
    // Pantalla modal simple: fondo semitransparente + tarjeta centrada
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        // fondo
        Surface(modifier = Modifier.fillMaxSize(), color = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.4f)) {}

        Card(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            var rating by remember { mutableStateOf(5) }
            var comentario by remember { mutableStateOf("") }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Dejar reseña", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = servicio.nombre, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(12.dp))

                // Rating simple: botones 1..5
                Row {
                    for (i in 1..5) {
                        TextButton(onClick = { rating = i }) {
                            Text(text = if (i <= rating) "★" else "☆", style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }

                OutlinedTextField(
                    value = comentario,
                    onValueChange = { comentario = it },
                    label = { Text("Comentario (opcional)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = onClose) { Text("Cerrar") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onSubmit(rating, comentario) }) {
                        Text("Enviar")
                    }
                }
            }
        }
    }
}

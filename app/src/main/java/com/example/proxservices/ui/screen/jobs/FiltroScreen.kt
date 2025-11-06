package com.example.proto.ui.screen.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FiltrosScreen(
    onVolverBusqueda: () -> Unit = {},
    onAplicarFiltros: () -> Unit = {}
) {
    val turquesa = Color(0xFF00BCD4)
    val grisClaro = Color(0xFFF7F7F7)

    var ubicacion by remember { mutableStateOf("") }
    var tipoServicio by remember { mutableStateOf("") }
    var calificacionMinima by remember { mutableStateOf("Sin filtro") }
    var radioBusqueda by remember { mutableStateOf(7f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(grisClaro)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Filtros", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            IconButton(onClick = { onVolverBusqueda() }) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Ubicación", fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = ubicacion,
            onValueChange = { ubicacion = it },
            placeholder = { Text("Choose option...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Radio de búsqueda", fontWeight = FontWeight.Bold)
        Slider(value = radioBusqueda, onValueChange = { radioBusqueda = it }, valueRange = 1f..20f)
        Text("${radioBusqueda.toInt()} km")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Tipo de servicio", fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = tipoServicio,
            onValueChange = { tipoServicio = it },
            placeholder = { Text("Choose option...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Calificación mínima", fontWeight = FontWeight.Bold)
        Text(calificacionMinima, color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    ubicacion = ""
                    tipoServicio = ""
                    calificacionMinima = "Sin filtro"
                    radioBusqueda = 7f
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text("Limpiar", color = Color.Black)
            }

            Button(
                onClick = { onAplicarFiltros() },
                colors = ButtonDefaults.buttonColors(containerColor = turquesa)
            ) {
                Text("Aplicar Filtros", color = Color.White)
            }
        }
    }
}

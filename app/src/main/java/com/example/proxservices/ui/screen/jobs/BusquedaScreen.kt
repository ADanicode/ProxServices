package com.example.proto.ui.screen.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.example.proxservices.ui.screen.client.ServicesSceen

data class Proveedor(
    val nombre: String,
    val oficio: String,
    val descripcion: String,
    val calificacion: Double,
    val precio: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusquedaScreen(
    navController: NavController,
    onBackClick: () -> Unit = {},
    onFiltroClick: () -> Unit = {},
    onContactarClick: () -> Unit = {}
) {
    // 🎨 Colores
    val fondo = Color(0xFFF7F7F7)
    val grisTexto = Color(0xFF555555)
    val turquesa = Color(0xFF00BCD4)
    val blanco = Color.White

    var query by remember { mutableStateOf("Plomer ") }

    // 🧑‍🔧 Lista de proveedores
    val proveedores = listOf(
        Proveedor(
            "María González",
            "Limpieza del Hogar",
            "Especialista en limpieza profunda con 5 años de experiencia",
            4.8,
            "$25/hora"
        ),
        Proveedor(
            "Carlos Mendoza",
            "Plomería",
            "Plomero certificado, reparaciones y mantenimiento",
            4.9,
            "$40/hora"
        ),
        Proveedor(
            "Sofía Martín",
            "Carpintería",
            "Carpintera especializada en muebles a medida",
            4.7,
            "$35/hora"
        ),
        Proveedor(
            "Luis Rodríguez",
            "Electricidad",
            "Electricista con certificación industrial",
            4.6,
            "$45/hora"
        ),
        Proveedor(
            "Ana Torres",
            "Jardinería",
            "Paisajista y especialista en mantenimiento de jardines",
            4.8,
            "$30/hora"
        )
    )
    var showBusqueda by remember { mutableStateOf(false) }
    // 🔎 Filtrar resultados según búsqueda
    val resultados = proveedores.filter {
        it.oficio.contains(query, ignoreCase = true)
    }
    if (showBusqueda) {
        // 🔹 Muestra la pantalla de búsqueda cuando se da clic
        ServicesSceen(
        navController =  navController)
    } else {

        Scaffold(
            containerColor = fondo,
            topBar = {
                Column(
                    modifier = Modifier
                        .background(blanco)
                        .padding(16.dp)
                ) {
                    // Encabezado
                 /*   Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { showBusqueda = true },
                            modifier = Modifier.size(32.dp)) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Atrás",
                                tint = Color.Black
                            )
                        }
                       /* Column {
                            Text(
                                "Búsqueda Avanzada",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                            Text(
                                "Encuentra el servicio perfecto",
                                fontSize = 13.sp,
                                color = Color.Gray
                            )
                        }*/
                    }
*/
                    Spacer(modifier = Modifier.height(12.dp))

                    // Barra de búsqueda
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, shape = MaterialTheme.shapes.medium)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)

                        TextField(
                            value = query,
                            onValueChange = { query = it },
                            placeholder = {
                                Text(
                                    "Buscar oficio o palabra clave...",
                                    color = Color.Gray
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                cursorColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        /* IconButton(onClick = { onFiltroClick() }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Filtro",
                            tint = Color.White,
                            modifier = Modifier
                                .background(turquesa, shape = CircleShape)
                                .padding(6.dp)
                        )
                    }*/
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Encabezado de resultados
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Resultados",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Text("${resultados.size} servicios", color = Color.Gray, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Lista de resultados
                LazyColumn {
                    items(resultados) { proveedor ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(containerColor = blanco),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Avatar
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(turquesa),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        proveedor.nombre.split(" ").map { it.first() }
                                            .joinToString(""),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                // Información
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        proveedor.nombre,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Text(proveedor.oficio, color = Color.Gray, fontSize = 14.sp)
                                    Text(proveedor.descripcion, color = grisTexto, fontSize = 12.sp)
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Default.Star,
                                            contentDescription = null,
                                            tint = Color(0xFFFFC107),
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            "${proveedor.calificacion}",
                                            color = grisTexto,
                                            fontSize = 13.sp
                                        )
                                    }
                                }

                                // Precio y botón
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        proveedor.precio,
                                        color = turquesa,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Button(
                                        onClick = { onContactarClick() },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4))
                                    ) {
                                        Text("Contactar", color = Color.White)
                                    }
                                }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


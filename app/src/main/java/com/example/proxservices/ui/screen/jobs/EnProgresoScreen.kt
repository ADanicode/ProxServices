package com.example.proto.ui.screen.jobs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

private val serviciosProgreso = listOf(
    Servicio("4","Ana Rodríguez", "Jardinería", "10 Mar 2024", "$90", "En Progreso", null),
    Servicio("5","Roberto Silva", "Pintura", "03 Mar 2024", "$180", "En Progreso", null)
)

@Composable
fun EnProgresoScreen(modifier: Modifier = Modifier,navController: NavController) {
    Column(modifier = modifier) {
        Text(text = "En Progreso", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(vertical = 8.dp))

        Column {
            serviciosProgreso.forEach { s ->
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
                            Text("Iniciado: ${s.fecha}", color = Color.Gray, fontSize = 12.sp)
                            Text("Servicio en curso", color = Color(0xFFFFB300), fontSize = 12.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(s.estado, color = Color(0xFFFFB300))
                            Text(s.precio, style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
        }
    }
}

package com.example.proxservices.ui.screen.workeri

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import com.example.proxservices.ui.theme.TextFieldBackground

// (Asumiendo que tienes este componente)
// import com.example.proxservices.ui.components.CustomAuthTextField

@Composable
fun EditProfileScreen(navController: NavController) {
    Scaffold(
        topBar = { EditProfileTopBar(navController) },
        containerColor = BackgroundLight
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            item {
                ProfileHeaderSection(
                    name = "María González",
                    profession = "Profesional de Servicios",
                    isAvailable = true
                )
                Spacer(modifier = Modifier.height(24.dp))

                PersonalDetailsSection()
                Spacer(modifier = Modifier.height(32.dp))

                MyServicesSection()
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

// --- Top Bar ---
@Composable
fun EditProfileTopBar(navController: NavController) {
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
            text = "Editar Perfil",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        TextButton(onClick = { /* Acción Guardar */ }) {
            Text(
                text = "Guardar",
                color = PrimaryCyan,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// --- Sección de Encabezado de Perfil (Imagen, Nombre, Estado) ---
@Composable
fun ProfileHeaderSection(name: String, profession: String, isAvailable: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Círculo de Imagen
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .border(2.dp, PrimaryCyan, CircleShape)
                .clickable { /* Abrir selector de imagen */ },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.CameraAlt,
                contentDescription = "Cambiar Foto",
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(profession, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Person, contentDescription = "Estado", tint = PrimaryCyan, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Estado:",
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isAvailable) "Disponible" else "Ocupado",
                color = if (isAvailable) PrimaryCyan else Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// --- Sección de Detalles Personales (Campos de Formulario) ---
@Composable
fun PersonalDetailsSection() {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Description, contentDescription = "Descripción", tint = PrimaryCyan, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Descripción Personal", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        }

        // Campo 'Sobre mí' (Descripción Multilínea)
        OutlinedTextField(
            value = "Escribe una descripción de ti...", // Estado real vendría del ViewModel
            onValueChange = { /* Actualizar ViewModel */ },
            label = { Text("Sobre mí") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = TextFieldBackground,
                focusedContainerColor = TextFieldBackground,
                focusedBorderColor = PrimaryCyan,
                unfocusedBorderColor = Color.Transparent
            )
        )

        // Campo Experiencia (Dropdown simulado)
        var selectedExperience by remember { mutableStateOf("1-3 años") }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(TextFieldBackground, RoundedCornerShape(8.dp))
                .clickable { /* Abrir menú dropdown */ }
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Experiencia: $selectedExperience")
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Experiencia")
            }
        }

        // Campo Ubicación (TextField simulado)
        OutlinedTextField(
            value = "Tu ubicación", // Estado real vendría del ViewModel
            onValueChange = { /* Actualizar ViewModel */ },
            label = { Text("Ubicación") },
            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = "Ubicación") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = TextFieldBackground,
                focusedContainerColor = TextFieldBackground,
                focusedBorderColor = PrimaryCyan,
                unfocusedBorderColor = Color.Transparent
            )
        )
    }
}

// --- Sección de Mis Servicios ---
@Composable
fun MyServicesSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Home, contentDescription = "Mis Servicios", tint = PrimaryCyan, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Mis Servicios", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            }
            IconButton(onClick = { /* Agregar nuevo servicio */ }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Servicio", tint = PrimaryCyan)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de Servicios (Composable para cada fila)
        ServiceRow(name = "Limpieza del Hogar", price = 25f, isChecked = true)
        ServiceRow(name = "Jardinería", price = 30f, isChecked = true)
        ServiceRow(name = "Plomería Básica", price = 40f, isChecked = false)
    }
}

// --- Fila de Servicio Individual ---
@Composable
fun ServiceRow(name: String, price: Float, isChecked: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { /* Toggle Service Active */ },
                colors = CheckboxDefaults.colors(checkedColor = PrimaryCyan)
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                Text("$${price}/hora", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Botón de Enlace/Precio
            IconButton(onClick = { /* Editar precio */ }) {
                Icon(Icons.Default.Link, contentDescription = "Editar Precio", tint = Color.Gray)
            }
            // Botón de Eliminar
            IconButton(onClick = { /* Eliminar servicio */ }) {
                Icon(Icons.Default.Close, contentDescription = "Eliminar", tint = Color.Red)
            }
        }
    }
    Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
}
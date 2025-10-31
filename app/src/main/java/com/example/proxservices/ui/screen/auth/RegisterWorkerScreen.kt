package com.example.proxservices.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proxservices.R
import com.example.proxservices.ui.navigation.Screen
import com.example.proxservices.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterWorkerScreen(navController: NavController) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var profession by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Para el Dropdown
    var professionExpanded by remember { mutableStateOf(false) }
    val professions = listOf("Plomería", "Electricidad", "Carpintería", "Jardinería", "Otro")
    var selectedProfession by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // CAMBIA ESTO por tu logo
                contentDescription = "Logo",
                modifier = Modifier.size(80.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Crear Cuenta como Trabajador",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextBlack
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Únete a nuestra plataforma y conecta con clientes que necesitan tus servicios profesionales.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(Modifier.height(32.dp))

            // --- Campos de Texto ---
            CustomAuthTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Nombre Completo",
                placeholder = "Ingresa tu nombre completo"
            )
            Spacer(Modifier.height(16.dp))
            CustomAuthTextField(
                value = email,
                onValueChange = { email = it },
                label = "Correo Electrónico",
                placeholder = "ejemplo@correo.com",
                keyboardType = KeyboardType.Email
            )
            Spacer(Modifier.height(16.dp))
            CustomAuthTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña",
                placeholder = "Mínimo 8 caracteres",
                keyboardType = KeyboardType.Password,
                isPassword = true
            )
            Spacer(Modifier.height(16.dp))
            CustomAuthTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirmar Contraseña",
                placeholder = "Repite tu contraseña",
                keyboardType = KeyboardType.Password,
                isPassword = true
            )
            Spacer(Modifier.height(16.dp))

            // --- Dropdown de Oficio Principal ---
            ExposedDropdownMenuBox(
                expanded = professionExpanded,
                onExpandedChange = { professionExpanded = !professionExpanded }
            ) {
                OutlinedTextField(
                    value = selectedProfession,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Oficio Principal") },
                    placeholder = { Text("Selecciona tu oficio principal", color = TextGray) },
                    trailingIcon = {
                        Icon(Icons.Default.ExpandMore, contentDescription = null, tint = TextGray) // <-- ¡¡CORREGIDO!!
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = CardBorder,
                        focusedBorderColor = PrimaryCyan
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(16.dp)
                )
                ExposedDropdownMenu(
                    expanded = professionExpanded,
                    onDismissRequest = { professionExpanded = false }
                ) {
                    professions.forEach { profession ->
                        DropdownMenuItem(
                            text = { Text(profession) },
                            onClick = {
                                selectedProfession = profession
                                professionExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = { /* TODO: Lógica de registro simulada */ },
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(top = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan), // Color oscuro
                shape = RoundedCornerShape(16.dp),
                enabled = !isLoading
            ) {
                Text("Crear Cuenta Como Trabajador", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("¿Ya tienes una cuenta?", color = TextGray, fontSize = 14.sp)
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Iniciar Sesión",
                    color = TextLink,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.navigate(Screen.Login.route) }
                )
            }
            Spacer(Modifier.height(64.dp)) // Más espacio al final
        }
    }
}

// Reutilizamos el Composable de RegisterClientScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomAuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder, color = TextGray) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                Icon(Icons.Default.Lock, contentDescription = null, tint = TextGray) // <-- ¡¡CORREGIDO!!
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = CardBorder,
            focusedBorderColor = PrimaryCyan
        )
    )
}

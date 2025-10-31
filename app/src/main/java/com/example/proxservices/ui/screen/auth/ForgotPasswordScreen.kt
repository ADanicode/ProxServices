package com.example.proxservices.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource // <-- 1. IMPORTAR
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proxservices.R
import com.example.proxservices.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var otpCode by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // CAMBIA ESTO por tu logo
                contentDescription = "Logo",
                modifier = Modifier.size(80.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Ingresa tu Código de Verificación",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextBlack
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Hemos enviado un código de 6 dígitos al correo electrónico registrado.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(Modifier.height(32.dp))

            // --- Campos de OTP ---
            OtpTextField(
                otpText = otpCode,
                onOtpTextChange = {
                    if (it.length <= 6) {
                        otpCode = it
                    }
                }
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Reenviar código",
                color = TextLink,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() }, // <-- 2. APLICAR CORRECCIÓN
                    indication = null, // <-- 3. APLICAR CORRECCIÓN
                    onClick = { /* TODO: Lógica de reenviar código */ }
                )
            )

            Spacer(Modifier.weight(1f)) // Espacio flexible

            Button(
                onClick = { /* TODO: Lógica de verificar código */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan),
                shape = RoundedCornerShape(16.dp),
                enabled = !isLoading && otpCode.length == 6
            ) {
                Text("Verificar y Continuar", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "¿No recibiste el código? Revisa tu carpeta de spam o solicita un nuevo código.",
                style = MaterialTheme.typography.bodySmall,
                color = TextGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}

// --- Composable para los campos de OTP ---
@Composable
fun OtpTextField(
    otpText: String,
    onOtpTextChange: (String) -> Unit,
    otpCount: Int = 6
) {
    BasicTextField(
        value = otpText,
        onValueChange = onOtpTextChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                repeat(otpCount) { index ->
                    val char = when {
                        index < otpText.length -> otpText[index].toString()
                        else -> ""
                    }
                    val isFocused = otpText.length == index

                    Box(
                        modifier = Modifier
                            .size(width = 48.dp, height = 56.dp)
                            .background(Color.Transparent, RoundedCornerShape(12.dp))
                            .border(
                                width = 1.dp,
                                color = if (isFocused) PrimaryCyan else CardBorder,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char,
                            fontSize = 20.sp,
                            color = TextBlack,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    )
}

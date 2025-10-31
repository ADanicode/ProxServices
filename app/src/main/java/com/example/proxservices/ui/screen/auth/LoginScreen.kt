package com.example.proxservices.ui.screen.auth
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proxservices.R
import com.example.proxservices.ui.components.InfoTagsRow
import com.example.proxservices.ui.navigation.Screen
import com.example.proxservices.ui.theme.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }
    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            // Navegamos al dashboard y limpiamos la pila de atrás
            navController.navigate(Screen.ClientDashboard.route) {
                popUpTo(Screen.Welcome.route) { inclusive = true }
            }
        }
    }
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

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
                text = "Iniciar Sesión",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextBlack
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Accede a tu cuenta para conectar con clientes y gestionar tus servicios profesionales",
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(Modifier.height(32.dp))


            OutlinedTextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("Correo Electrónico") },
                placeholder = { Text("Ingresa tu correo", color = TextGray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                isError = uiState.errorMessage != null,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = CardBorder,
                    focusedBorderColor = PrimaryCyan
                )
            )

            Spacer(Modifier.height(16.dp))


            OutlinedTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text("Contraseña") },
                placeholder = { Text("Ingresa tu contraseña", color = TextGray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                            tint = TextGray
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                isError = uiState.errorMessage != null,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = CardBorder,
                    focusedBorderColor = PrimaryCyan
                )
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "¿Olvidaste tu Contraseña?",
                color = TextLink,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { navController.navigate(Screen.ForgotPassword.route) }
                    )
            )

            Spacer(Modifier.weight(1f)) // Espacio flexible

            Button(
                onClick = viewModel::onLoginClick,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan),
                shape = RoundedCornerShape(16.dp),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Entrar", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            Spacer(Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("¿No tienes una cuenta?", color = TextGray, fontSize = 14.sp)
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Crear Cuenta",
                    color = TextLink,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { navController.navigate(Screen.RegisterClient.route) }
                    )
                )
            }

            Spacer(Modifier.height(32.dp))
            InfoTagsRow(modifier = Modifier.padding(bottom = 32.dp))
        }
    }
}


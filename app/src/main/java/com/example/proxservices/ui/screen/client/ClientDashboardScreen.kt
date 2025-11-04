package com.example.proxservices.ui.screen.client

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ClientDashboardScreen(
    navController: NavController,
    viewModel: ClientDashboardViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            // ¡¡AQUÍ ESTÁ LA CONEXIÓN!!
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Bienvenido,")
                Text(text = uiState.userName) // <-- Muestra el nombre "Juan Pérez (Cliente)"
            }
        }
    }
}

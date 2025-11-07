package com.example.proxservices.ui.screen.workeri

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.ui.graphics.vector.ImageVector

sealed class WorkerNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    data object Home : WorkerNavItem("worker_home", Icons.Filled.Home, "Inicio")
    data object Jobs : WorkerNavItem("worker_jobs", Icons.Filled.Task, "Trabajos")
    data object Wallet : WorkerNavItem("worker_wallet", Icons.Filled.Wallet, "Billetera")
    data object Chat : WorkerNavItem("worker_chat", Icons.Filled.Chat, "Chat")
}
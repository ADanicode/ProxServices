package com.example.proxservices.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.BorderStroke // AGREGAR ESTA LÍNEA
import androidx.compose.ui.unit.dp // AGREGAR ESTA LÍNEA

// Paleta de colores basada en tu nuevo Figma (Tema Claro)

// Colores Primarios
val PrimaryCyan = Color(0xFF18D8C2) // Botones principales
val PrimaryCyanLight = Color(0xFF74E4DA) // Botón "Crear Cuenta" de Cliente

// Colores de Texto
val TextBlack = Color(0xFF1D1D1F) // Títulos y texto principal
val TextGray = Color(0xFF8A8A8E) // Subtítulos y placeholders
val TextLink = Color(0xFF007BFF) // Enlaces como "Iniciar Sesión"

// Colores de Fondo
val BackgroundLight = Color(0xFFFFFFFF) // Fondo principal de la app
val CardBorder = Color(0xFFEAEAEA) // Borde sutil para las "tarjetas" de texto
val TextFieldBackground = Color(0xFFF2F2F7) // <-- ¡LA LÍNEA QUE FALTABA!

// Colores de Tags
val TagBlue = Color(0xFF007BFF)
val TagGreen = Color(0xFF34C759) // Para el chat "en línea"

// --- NUEVOS COLORES PARA EL FLUJO DE TRABAJADOR (WORKERi) ---
// Agregados para el Dashboard y Editar Perfil
val PrimaryPurple = Color(0xFF5A55FF) // Color del botón de Billetera
val StarYellow = Color(0xFFFFC107) // Color estándar para las estrellas de reputación
// -------------------------------------------------------------
// --- AGREGADO PARA RESOLVER EL ERROR EN WORKERDASHBOARDSCREEN ---
val CardBorderStroke = BorderStroke(1.dp, CardBorder) // Usa tu CardBorder existente como Color
package com.example.proxservices.ui.theme

import androidx.compose.ui.graphics.Color

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

// 📁 com.example.proxservices.ui.theme.Color.kt (Añadir al final del archivo)

// Colores para la funcionalidad de la Cartera
val PrimaryBlue = PrimaryCyan // Usaremos Cyan como nuestro PrimaryBlue
val DarkBlueText = Color(0xFF1F414F) // Azul Oscuro (para saldos principales)
val ScreenBackground = BackgroundLight // Usaremos BackgroundLight como fondo
val TextPrimary = TextBlack // Usaremos TextBlack como TextPrimary
val TextSecondary = TextGray // Usaremos TextGray como TextSecondary

val DarkPurple = Color(0xFF512DA8) // Morado oscuro para el indicador de pestañas

// Colores específicos para Historial y UI
val PointsPositive = PrimaryCyan // Cian para ganancias
val PointsNegative = Color(0xFFEB5757) // Rojo para gastos/restas
val DividerColor = Color(0xFFDEDEE0) // Gris suave
val StarColor = Color(0xFFFFC107) // Color para estrellas
val White = Color(0xFFFFFFFF)


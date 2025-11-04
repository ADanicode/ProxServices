package com.example.proxservices.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect // Lo mantenemos para el futuro, pero vacío
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LightColorScheme = lightColorScheme(
    primary = PrimaryCyan,
    background = BackgroundLight,
    surface = BackgroundLight,
    onPrimary = Color.White,
    onBackground = TextBlack,
    onSurface = TextBlack,
    secondary = TextGray,
    surfaceVariant = BackgroundLight,
    outline = CardBorder
)

@Composable
fun ProxServicesTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {

        //
        // ¡¡AQUÍ ESTÁ LA CORRECCIÓN!!
        //
        // Hemos eliminado el bloque SideEffect que intentaba
        // controlar la barra de estado.
        // La API 'installSplashScreen()' en MainActivity
        // ya se encarga de esto de forma segura.
        //
        // SideEffect { ... } // <-- BLOQUE ELIMINADO
        //
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


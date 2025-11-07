package com.example.proxservices.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proxservices.data.model.Transaction // ⭐ El modelo ahora tiene 'money'
import com.example.proxservices.ui.theme.* import androidx.compose.ui.graphics.Color
import com.example.proxservices.ui.theme.BackgroundLight

@Composable
fun HistoryItem(transaction: Transaction) {
    // ⭐ CORRECCIÓN: Usar 'transaction.money'
    val isPositive = transaction.money >= 0
    val pointColor = if (isPositive) PointsPositive else PointsNegative

    // El signo de dólar se añade solo si es negativo (gasto),
    // ya que el color (PointsPositive) indica ganancia para los positivos.
    val moneySign = if (isPositive) "+" else "-" // Usamos el signo del número
    val moneyDisplay = if (isPositive) transaction.money.toString() else (-transaction.money).toString()


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // CONTENEDOR CIRCULAR (Azul o Rojo)
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(pointColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when {
                    transaction.sourceDetails?.contains("Calificación") == true -> Icons.Default.Verified
                    isPositive -> Icons.Default.ArrowUpward
                    else -> Icons.Default.Redeem
                },
                contentDescription = null,
                tint = BackgroundLight, // ÍCONO BLANCO
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            // Título
            Text(text = transaction.description, color = TextPrimary, fontSize = 16.sp, style = MaterialTheme.typography.titleMedium)
            // Detalles (Calificación o Tienda)
            if (transaction.sourceDetails != null) {
                Text(text = transaction.sourceDetails, color = TextSecondary, fontSize = 12.sp, style = MaterialTheme.typography.labelSmall)
            }
            // Fecha/Hora
            Text(text = transaction.date, color = TextSecondary, fontSize = 12.sp, style = MaterialTheme.typography.labelSmall)
        }

        // ⭐ MONTO DE DINERO: Muestra el valor con el signo y el formato.
        Text(
            text = "$moneySign$moneyDisplay", // Muestra: +$50 o -$10
            color = pointColor,
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
    HorizontalDivider(color = DividerColor, thickness = 0.5.dp, modifier = Modifier.padding(start = 40.dp))
}
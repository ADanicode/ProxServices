package com.example.proxservices.ui.screen.worker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.* import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proxservices.data.model.RedeemableItem
import com.example.proxservices.ui.navigation.Screen
import com.example.proxservices.ui.theme.* import com.example.proxservices.viewmodel.WalletViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RedeemDetailScreen(
    navController: NavController,
    itemId: String?,
    viewModel: WalletViewModel = viewModel()
) {
    val item = viewModel.redeemableItems.find { it.id == itemId }
    // ⭐ RENOMBRADO: currentMoney (Double)
    val currentMoney by viewModel.currentMoney

    val stockStatusColor = if (item != null && item.stock != null && item.stock <= 5 && item.stock > 0) Color.Red else TextPrimary

    if (item == null) {
        Scaffold(topBar = { DetailHeader(navController, "Artículo no válido") }) { paddingValues ->
            Box(Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Artículo no encontrado.", color = TextSecondary)
            }
        }
        return
    }

    Scaffold(
        containerColor = ScreenBackground,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {
            DetailHeader(navController = navController, title = item.name)

            Spacer(modifier = Modifier.height(16.dp))

            // Imagen del producto (Placeholder)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("[Imagen de ${item.name}]", color = TextSecondary)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                // ⭐ COSTE DEL ARTÍCULO (Manejo de Dinero)
                Text(
                    text = "$${item.moneyCost}.00", // Asumimos que el costo es el valor en dinero
                    color = PrimaryBlue,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                HorizontalDivider(color = DividerColor, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))

                // Detalles Adicionales
                DetailRow(label = "Categoría") {
                    Text(text = item.category ?: "N/A", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
                DetailRow(label = "Valor comercial") { // Texto corregido
                    Text(text = item.currentValue ?: "N/A", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }

                DetailRow(label = "Disponibilidad") {
                    Text(
                        text = if (item.stock != null) "${item.stock} unidades" else "N/A",
                        color = stockStatusColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Restricciones
                if (item.restrictions != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Restricciones:", fontWeight = FontWeight.SemiBold, color = TextPrimary, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = item.restrictions, color = TextSecondary, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ⭐ ConfirmRedeemButton (usa currentMoney)
                ConfirmRedeemButton(
                    item = item,
                    currentMoney = currentMoney, // Pasa el valor Double
                    viewModel = viewModel,
                    navController = navController,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// ----------------------------------------------------------------------------------
// COMPONENTES AUXILIARES
// ----------------------------------------------------------------------------------
@Composable
fun DetailRow(label: String, content: @Composable () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = TextSecondary, fontSize = 14.sp)
        content()
    }
}

@Composable
fun DetailHeader(navController: NavController, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(24.dp))
        ) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás", tint = PrimaryBlue, modifier = Modifier.size(32.dp))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
    }
}

@Composable
fun ConfirmRedeemButton(
    item: RedeemableItem,
    currentMoney: Double, // ⭐ RENOMBRADO A DINERO
    viewModel: WalletViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // La comparación es Double >= Int (Int se promociona a Double)
    val costDouble = item.moneyCost.toDouble()
    val canRedeem = currentMoney >= costDouble && (item.stock == null || item.stock > 0)
    // ⭐ TEXTO DEL BOTÓN: Ahora usa "Costo" en lugar de "Puntos"
    val buttonText = if (canRedeem) "Confirmar Canje de $$costDouble" else "Saldo Insuficiente"

    Button(
        onClick = {
            viewModel.redeemItem(item)
            navController.popBackStack(Screen.WorkerWallet.route, inclusive = false)
        },
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 8.dp),
        enabled = canRedeem,
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = buttonText,
            color = White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
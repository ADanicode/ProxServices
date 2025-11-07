package com.example.proxservices.ui.screen.worker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proxservices.ui.components.HistoryItem
import com.example.proxservices.ui.navigation.Screen
import com.example.proxservices.ui.theme.* import com.example.proxservices.viewmodel.WalletViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WalletScreen(
    navController: NavController,
    viewModel: WalletViewModel = viewModel()
) {
    // ⭐ USO DE LA VARIABLE CORREGIDA: currentMoney (Double)
    val currentMoney by viewModel.currentMoney
    val filterType by viewModel.filterType
    val filteredTransactions by viewModel.filteredTransactions
    val monetaryEquivalent by viewModel.monetaryEquivalent

    val tabs = listOf("Todos", "Obtenidos", "Canjeados")

    Scaffold(
        containerColor = ScreenBackground
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))

                WalletHeaderCard()

                Spacer(modifier = Modifier.height(16.dp))

                // Pasamos el saldo Double y el String formateado
                PointsCard(
                    currentMoney = currentMoney,
                    monetaryEquivalent = monetaryEquivalent,
                    onRedeemClick = { navController.navigate(Screen.WorkerCatalog.route) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Historial de Puntos",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                TabRow(
                    selectedTabIndex = filterType,
                    containerColor = White,
                    contentColor = PrimaryBlue,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = filterType == index,
                            onClick = { viewModel.setFilter(index) },
                            text = { Text(title) }
                        )
                    }
                }
            }

            // Items del Historial
            items(filteredTransactions) { transaction ->
                HistoryItem(transaction = transaction)
            }
        }
    }
}


// ----------------------------------------------------------------------------------
// COMPONENTES AUXILIARES
// ----------------------------------------------------------------------------------

@Composable
fun WalletHeaderCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(PrimaryBlue),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, contentDescription = "Usuario", tint = White, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = "Mi Billetera", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "Gestiona tus fondos", color = TextSecondary, fontSize = 13.sp)
        }
    }
}


@Composable
// ⭐ El nombre del parámetro es ahora currentMoney (Double)
fun PointsCard(currentMoney: Double, monetaryEquivalent: String, onRedeemClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF515BD4), Color(0xFF13E7F8)),
                        startX = 0f,
                        endX = Float.POSITIVE_INFINITY
                    )
                )
                .padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Saldo Actual", color = White, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))

                    // ⭐ VISUALIZACIÓN: Usa el String formateado para el saldo
                    Text(
                        text = "$$monetaryEquivalent",
                        color = White,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "(Equivalente a $$monetaryEquivalent USD)",
                        color = White.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Disponible para retiro", color = White.copy(alpha = 0.8f), fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onRedeemClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = PrimaryBlue
                ),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(text = "Canjear Productos", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
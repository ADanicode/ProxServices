package com.example.proxservices.ui.screen.worker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
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
import java.util.Locale

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CatalogScreen(
    navController: NavController,
    viewModel: WalletViewModel = viewModel()
) {
    // ⭐ RENOMBRADO: currentMoney (Double)
    val currentMoney by viewModel.currentMoney

    // Formatear el saldo principal a string de dinero
    val formattedBalance = String.format(Locale.US, "%.2f", currentMoney)

    Scaffold(
        containerColor = ScreenBackground
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    CatalogHeader(navController = navController)
                    Spacer(modifier = Modifier.height(16.dp))
                    CompactMoneyCard(formattedBalance = formattedBalance) // ⭐ RENOMBRADO
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            items(viewModel.redeemableItems) { item ->
                RedeemableItemCard(item = item) {
                    navController.navigate(Screen.WorkerRedeemDetail.createRoute(item.id))
                }
            }
        }
    }
}

// ----------------------------------------------------------------------------------
// 1. COMPONENTE: Encabezado del Catálogo
// ----------------------------------------------------------------------------------
@Composable
fun CatalogHeader(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigateUp() }, modifier = Modifier.size(48.dp).clip(RoundedCornerShape(24.dp))) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás", tint = PrimaryBlue, modifier = Modifier.size(32.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "Catálogo de Canje", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "Encuentra tus recompensas aquí", color = TextSecondary, fontSize = 13.sp)
            }
        }

        IconButton(onClick = { /* Lógica de búsqueda */ }, modifier = Modifier.size(48.dp).clip(RoundedCornerShape(24.dp))) {
            Icon(Icons.Filled.Search, contentDescription = "Buscar", tint = TextPrimary, modifier = Modifier.size(24.dp))
        }
    }
}

// ----------------------------------------------------------------------------------
// 2. COMPONENTE: Tarjeta Compacta de Dinero Disponible (RENOMBRADO)
// ----------------------------------------------------------------------------------
@Composable
fun CompactMoneyCard(formattedBalance: String) { // ⭐ RENOMBRADO
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Saldo Disponible:", color = TextSecondary, fontSize = 14.sp)
                Text(
                    text = "$$formattedBalance",
                    color = DarkBlueText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ----------------------------------------------------------------------------------
// 3. COMPONENTE: Tarjeta de Artículo Canjeable (RedeemableItemCard)
// ----------------------------------------------------------------------------------
@Composable
fun RedeemableItemCard(item: RedeemableItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Bloque Superior (Nombre, Valor)
            Column {
                Text(text = item.name, color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(4.dp))
                if (item.currentValue != null) {
                    Text(text = "Valor: ${item.currentValue}", color = TextSecondary, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Bloque Inferior (Costo, Stock/Rating)
            Column {
                // ⭐ COSTO EN DINERO
                Text(text = "$${item.moneyCost}.00", color = PrimaryBlue, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))

                if (item.stock != null && item.stock <= 5) {
                    Text(text = "¡Stock Bajo! (${item.stock})", color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                } else if (item.rating != null && item.rating > 0f) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Star, contentDescription = "Calificación", tint = StarColor, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = item.rating.toString(), color = TextSecondary, fontSize = 12.sp)
                    }
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
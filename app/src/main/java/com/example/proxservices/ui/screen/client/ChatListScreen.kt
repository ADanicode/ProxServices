package com.example.proxservices.ui.screen.client

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proxservices.R
import com.example.proxservices.ui.theme.*

// Modelo de datos simulado para un contacto de chat
data class ChatContact(
    val id: String,
    val name: String,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int,
    val isOnline: Boolean
)

// Lista simulada de contactos de chat
val chatList = listOf(
    ChatContact("1", "Juan Pérez (Plomero)", "¡Claro! Llego en 10 minutos.", "10:30 a.m.", 1, true),
    ChatContact("2", "María González (Electricista)", "El trabajo está completado.", "Ayer", 0, false),
    ChatContact("3", "Carlos (Jardinero)", "Gracias por tu reseña.", "Domingo", 0, true)
)

/**
 * Pantalla que muestra la lista de conversaciones (estilo WhatsApp)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    navController: NavController,
    onNavigateToChatDetail: (String) -> Unit // <-- ¡PARÁMETRO CORREGIDO!
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chats", fontWeight = FontWeight.Bold, color = TextBlack) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(chatList) { chat ->
                ChatListItem(
                    chat = chat,
                    onClick = {
                        // Pasa el evento de navegación al "padre" (ClientMainScreen)
                        onNavigateToChatDetail(chat.name)
                    }
                )
                Divider(color = CardBorder, modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

/**
 * Un solo ítem (fila) en la lista de chats.
 */
@Composable
fun ChatListItem(chat: ChatContact, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null, // <-- ¡ARREGLO DEL CRASH!
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- Foto de Perfil con indicador "en línea" ---
        Box(contentAlignment = Alignment.BottomEnd) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_round), // Icono de app como placeholder
                contentDescription = chat.name,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            if (chat.isOnline) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(TagGreen, CircleShape)
                        .border(2.dp, BackgroundLight, CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // --- Nombre y Último Mensaje ---
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = chat.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextBlack
            )
            Text(
                text = chat.lastMessage,
                fontSize = 14.sp,
                color = if (chat.unreadCount > 0) TextBlack else TextGray,
                fontWeight = if (chat.unreadCount > 0) FontWeight.Bold else FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // --- Hora y Contador de No Leídos ---
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = chat.timestamp,
                fontSize = 12.sp,
                color = TextGray
            )
            if (chat.unreadCount > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(PrimaryCyan, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = chat.unreadCount.toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                // Espacio vacío para alinear
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatListScreenPreview() {
    ProxServicesTheme {
        ChatListScreen(
            navController = rememberNavController(),
            onNavigateToChatDetail = {}
        )
    }
}


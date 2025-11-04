package com.example.proxservices.ui.screen.client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proxservices.ui.theme.*
import kotlinx.coroutines.launch

// Data class simple para representar un mensaje
private data class ChatMessage(
    val id: Long = System.currentTimeMillis(),
    val text: String,
    val isSentByUser: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    navController: NavController,
    contactName: String // <-- Recibe el nombre desde el NavGraph
) {
    // Estado para guardar el mensaje que el usuario está escribiendo
    var messageText by remember { mutableStateOf("") }
    // Estado para guardar la lista de todos los mensajes en la conversación
    var messages by remember { mutableStateOf(listOf<ChatMessage>()) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // --- Lógica de la auto-respuesta ---
    fun onSendMessage() {
        if (messageText.isBlank()) return // No enviar mensajes vacíos

        // 1. Crea el mensaje del usuario
        val userMessage = ChatMessage(
            text = messageText,
            isSentByUser = true
        )

        // 2. Crea la respuesta automática
        val autoResponse = ChatMessage(
            text = "No disponible por el momento.",
            isSentByUser = false
        )

        // 3. Añade ambos mensajes a la lista
        messages = messages + userMessage + autoResponse

        // 4. Limpia el campo de texto
        messageText = ""

        // 5. Auto-scroll al final de la lista
        coroutineScope.launch {
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }
    // --- Fin de la lógica ---

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = contactName,
                        fontWeight = FontWeight.Bold,
                        color = TextBlack,
                        fontSize = 18.sp // Un poco más pequeño para nombres largos
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = TextBlack)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // --- Área de Mensajes ---
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                reverseLayout = true // <-- Hace que la lista empiece desde abajo
            ) {
                // Invertimos la lista para mostrarla correctamente con reverseLayout
                items(messages.reversed()) { message ->
                    ChatBubble(message = message)
                }
            }

            // --- Barra de Entrada de Texto ---
            Divider(color = CardBorder)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundLight)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("Escribe un mensaje...", color = TextGray) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = CardBorder,
                        focusedBorderColor = PrimaryCyan,
                        containerColor = TextFieldBackground
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = ::onSendMessage,
                    modifier = Modifier
                        .size(48.dp)
                        .background(PrimaryCyan, CircleShape),
                    enabled = messageText.isNotBlank() // El botón solo se activa si hay texto
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Enviar",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

// Composable privado para las "burbujas" de chat
@Composable
private fun ChatBubble(message: ChatMessage) {
    // Determina el alineamiento y color basado en quién envió el mensaje
    val alignment = if (message.isSentByUser) Alignment.End else Alignment.Start
    val backgroundColor = if (message.isSentByUser) PrimaryCyan else TextFieldBackground
    val textColor = if (message.isSentByUser) Color.White else TextBlack

    // Forma de la burbuja (para que tenga la "colita")
    val bubbleShape = if (message.isSentByUser) {
        RoundedCornerShape(16.dp, 16.dp, 4.dp, 16.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 4.dp)
    }

    // Usa un Box para alinear la burbuja a la derecha o izquierda
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                // Añade padding para que la burbuja no ocupe toda la pantalla
                start = if (message.isSentByUser) 64.dp else 0.dp,
                end = if (message.isSentByUser) 0.dp else 64.dp
            ),
        contentAlignment = alignment as Alignment
    ) {
        Text(
            text = message.text,
            color = textColor,
            modifier = Modifier
                .clip(bubbleShape)
                .background(backgroundColor)
                .padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}


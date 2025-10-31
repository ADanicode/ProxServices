package com.example.proxservices.ui.screen.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
// import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
// import com.example.proxservices.R
import com.example.proxservices.ui.components.InfoTagsRow
import com.example.proxservices.ui.navigation.Screen
import com.example.proxservices.ui.theme.*

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        //
        // --- PASO 2: Comentamos la Imagen que causa el crash ---
        //
        /*
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // CAMBIA ESTO por tu logo
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp) // Ajusta el tamaño
        )
        */

        Spacer(modifier = Modifier.height(100.dp))


        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¡Bienvenido a tu nueva oportunidad!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = TextBlack,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Conectamos talento con oportunidades. Encuentra el trabajo perfecto o contrata a los mejores profesionales de manera rápida y segura.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))


        Button(
            onClick = { navController.navigate(Screen.RegisterClient.route) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
            Spacer(Modifier.width(8.dp))
            Text("Soy Cliente", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = { navController.navigate(Screen.RegisterWorker.route) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.BusinessCenter, contentDescription = null, tint = Color.White)
            Spacer(Modifier.width(8.dp))
            Text("Soy Trabajador", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "¿Ya tienes una cuenta?",
                color = TextGray,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Iniciar Sesión",
                color = TextLink,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null, // <-- ¡¡CORRECCIÓN!! Deshabilita el ripple de M2
                    onClick = { navController.navigate(Screen.Login.route) }
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        InfoTagsRow(modifier = Modifier.padding(bottom = 32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    ProxServicesTheme {
        WelcomeScreen(navController = rememberNavController())
    }
}


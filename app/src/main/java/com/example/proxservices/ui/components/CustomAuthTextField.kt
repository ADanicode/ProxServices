package com.example.proxservices.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.proxservices.ui.theme.CardBorder
import com.example.proxservices.ui.theme.PrimaryCyan
import com.example.proxservices.ui.theme.TextGray

/**
 * Un OutlinedTextField personalizado para las pantallas de autenticación.
 * Incluye la lógica para mostrar/ocultar la contraseña.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordToggleClick: () -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder, color = TextGray) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),

        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {

                IconButton(onClick = onPasswordToggleClick) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        tint = TextGray
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = CardBorder,
            focusedBorderColor = PrimaryCyan
        )
    )
}


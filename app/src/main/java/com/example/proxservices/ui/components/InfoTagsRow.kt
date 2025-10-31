package com.example.proxservices.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proxservices.ui.theme.TagBlue
import com.example.proxservices.ui.theme.TextGray

@Composable
fun InfoTagsRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TagChip("Seguro")
        Spacer(Modifier.width(16.dp))
        TagChip("Rápido")
        Spacer(Modifier.width(16.dp))
        TagChip("Confiable")
    }
}

@Composable
private fun TagChip(text: String, color: Color = TagBlue) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Circle,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(8.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = text,
            color = TextGray,
            fontSize = 14.sp
        )
    }
}
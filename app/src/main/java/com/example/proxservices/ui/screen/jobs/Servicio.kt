package com.example.proto.ui.screen.jobs

data class Servicio(
    val id: String,
    val nombre: String,
    val oficio: String,
    val fecha: String,
    val precio: String,      // usando formato con símbolo, p. ej. "$75"
    val estado: String,
    val rating: Double? = null
)

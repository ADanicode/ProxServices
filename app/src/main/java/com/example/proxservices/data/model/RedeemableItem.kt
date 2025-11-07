package com.example.proxservices.data.model

data class RedeemableItem(
    val id: String,
    val name: String,
    val category: String,
    val moneyCost: Int,
    val currentValue: String? = null,
    val stock: Int? = null,
    val restrictions: String? = null,
    val rating: Float = 0f,
    val isAvailable: Boolean = true
)
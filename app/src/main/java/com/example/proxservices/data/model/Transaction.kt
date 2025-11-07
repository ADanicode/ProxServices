package com.example.proxservices.data.model

data class Transaction(
    val id: String,
    val description: String,
    val date: String,
    val money: Int,
    val sourceDetails: String? = null
)
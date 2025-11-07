package com.example.proxservices.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proxservices.data.model.RedeemableItem
import com.example.proxservices.data.model.Transaction
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WalletViewModel : ViewModel() {

    // El saldo actual se llama ahora _currentMoney (Double)
    private val _currentMoney = mutableStateOf(1250.00)

    val currentMoney: State<Double>
        get() = _currentMoney

    // Formatea el saldo a String
    val monetaryEquivalent = derivedStateOf { String.format(Locale.US, "%.2f", _currentMoney.value) }

    private val _filterType = mutableStateOf(0)
    val filterType: State<Int> = _filterType

    // El historial usa la propiedad 'money' de Transaction
    private val _allTransactions = mutableStateListOf(
        Transaction("1", "Servicio de Plomería completado", "29/10/2025 - 16:30", 50, "Calificación 5★"),
        Transaction("2", "Cargo por descuento online", "27/10/2025 - 09:00", -10, "Tienda AMZ"),
        Transaction("3", "Servicio de Electricidad completado", "25/10/2025 - 14:15", 30, "Calificación 4★"),
        Transaction("4", "Canje - Kit de Limpieza", "24/10/2025 - 10:00", -500),
        Transaction("5", "Bono por meta trimestral", "01/10/2025 - 08:00", 200, "Bono Gerencial")
    )

    val filteredTransactions: State<List<Transaction>> = derivedStateOf {
        when (_filterType.value) {
            // Filtra por la propiedad 'money'
            1 -> _allTransactions.filter { it.money > 0 }
            2 -> _allTransactions.filter { it.money < 0 }
            else -> _allTransactions.toList()
        }
    }

    fun setFilter(type: Int) {
        _filterType.value = type
    }

    // Los artículos usan la propiedad 'moneyCost' de RedeemableItem
    val redeemableItems = listOf(
        RedeemableItem("A", "Kit Limpieza Profesional", "Herramientas", 500, stock = 3, restrictions = "Solo en almacén", rating = 4.2f),
        RedeemableItem("C", "Amazon - $50 Gift Card", "GiftCard", 400, currentValue = "$50 USD", rating = 4.5f),
        RedeemableItem("D", "Uniforme de Trabajo (Park 1)", "Herramientas", 180, stock = 10, isAvailable = true),
        RedeemableItem("K", "Entrenamiento HVAC Online", "Cursos", 800, rating = 4.9f),
        RedeemableItem("L", "Mochila de Herramientas", "Herramientas", 750, stock = 0, isAvailable = false)
    )

    // FUNCIÓN DE CANJE FINAL
    fun redeemItem(item: RedeemableItem) {
        // Usa item.moneyCost
        val costDouble = item.moneyCost.toDouble()
        val costInt = item.moneyCost

        if (_currentMoney.value >= costDouble) {
            _currentMoney.value -= costDouble // Resta de Double a Double

            val currentTime = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault()).format(Date())
            val newTransaction = Transaction(
                id = System.currentTimeMillis().toString(),
                description = "Canje: ${item.name}",
                date = currentTime,
                money = -costInt, // Guarda el costo en el campo 'money'
                sourceDetails = "Catálogo / ${item.category}"
            )
            _allTransactions.add(0, newTransaction)
        }
    }
}
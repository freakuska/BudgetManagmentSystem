package com.example.budget_management_system.utils

import java.text.NumberFormat
import java.util.*

object CurrencyUtils {

    fun formatCurrency(amount: Double, currency: String = "RUB"): String {
        val locale = when (currency) {
            "RUB" -> Locale("ru", "RU")
            "USD" -> Locale.US
            "EUR" -> Locale.GERMANY
            else -> Locale.getDefault()
        }

        val formatter = NumberFormat.getCurrencyInstance(locale)
        return formatter.format(amount)
    }

    fun formatAmount(amount: Double): String {
        return String.format("%.2f", amount)
    }

    fun parseAmount(text: String): Double? {
        return try {
            text.replace(",", ".").toDouble()
        } catch (e: Exception) {
            null
        }
    }

    fun getCurrencySymbol(currency: String): String {
        return when (currency) {
            "RUB" -> "₽"
            "USD" -> "$"
            "EUR" -> "€"
            else -> currency
        }
    }
}

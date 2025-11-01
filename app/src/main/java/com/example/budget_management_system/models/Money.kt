package com.example.budget_management_system.models

data class Money(
    val amount: Double, // decimal -> Double
    val currency: String // ISO-4217
) {
    companion object {
        fun from(amount: Double, currency: String): Money {
            require(currency.length == 3) { "Currency must be ISO-4217 (3 chars)." }
            return Money(amount, currency.uppercase())
        }
    }

    fun add(other: Money): Money {
        if (currency != other.currency) {
            throw IllegalStateException("Currencies must match")
        }
        return Money(amount + other.amount, currency)
    }
}
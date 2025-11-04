package com.example.budget_management_system.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val sdfISO = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private val sdfDisplay = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    private val sdfDateOnly = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private val sdfMonth = SimpleDateFormat("MMMM yyyy", Locale("ru", "RU"))

    fun formatToISO(date: Date): String {
        return sdfISO.format(date)
    }

    fun formatToDisplay(date: Date): String {
        return sdfDisplay.format(date)
    }

    fun formatDateOnly(date: Date): String {
        return sdfDateOnly.format(date)
    }

    fun formatMonth(date: Date): String {
        return sdfMonth.format(date)
    }

    fun parseISO(dateString: String): Date? {
        return try {
            sdfISO.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    fun getCurrentDate(): Date = Date()

    fun isToday(date: Date): Boolean {
        val today = Calendar.getInstance()
        val checkDate = Calendar.getInstance()
        checkDate.time = date

        return today.get(Calendar.YEAR) == checkDate.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == checkDate.get(Calendar.DAY_OF_YEAR)
    }

    fun startOfMonth(date: Date): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.set(Calendar.DAY_OF_MONTH, 1)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        return cal.time
    }

    fun endOfMonth(date: Date): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        cal.set(Calendar.HOUR_OF_DAY, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)
        return cal.time
    }
}

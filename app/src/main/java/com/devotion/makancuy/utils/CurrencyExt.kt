package com.devotion.makancuy.utils

import java.text.NumberFormat
import java.util.Locale

fun Double?.DoubleToCurrency(language: String, country: String): String? {
    return try {
        val localeID = Locale(language, country)
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.format(this).toString()
    } catch (e: Exception) {
        null
    }
}

fun Double?.toIndonesianFormat() = this.DoubleToCurrency("in", "ID")

fun String?.currencyToDouble(language: String, country: String): Int? {
    return try {
        val localeID = Locale(language, country)
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        val parsed = numberFormat.parse(this)
        parsed?.toInt()
    } catch (e: Exception) {
        null
    }
}

fun String?.fromCurrencyToInt(): Int? = this.currencyToDouble("in", "ID")
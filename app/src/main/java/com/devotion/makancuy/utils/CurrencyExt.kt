package com.devotion.makancuy.utils

import java.text.NumberFormat
import java.util.Locale

fun Int?.intToCurrency(language: String, country: String): String? {
    return try {
        val localeID = Locale(language, country)
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.format(this).toString()
    } catch (e: Exception) {
        null
    }
}

fun Int?.toIndonesianFormat() = this.intToCurrency("in", "ID")

fun String?.currencyToInt(language: String, country: String): Int? {
    return try {
        val localeID = Locale(language, country)
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        val parsed = numberFormat.parse(this)
        parsed?.toInt()
    } catch (e: Exception) {
        null
    }
}

fun String?.fromCurrencyToInt(): Int? = this.currencyToInt("in", "ID")
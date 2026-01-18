package com.cbmoney.utils

import android.content.Context
import java.text.NumberFormat
import java.util.Locale

fun Context.setAppLocale(language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val config = resources.configuration
    config.setLocale(locale)
    @Suppress("DEPRECATION")
    resources.updateConfiguration(config, resources.displayMetrics)
}
fun Long.formatMoney(): String =
    NumberFormat.getInstance(Locale("vi", "VN")).format(this)
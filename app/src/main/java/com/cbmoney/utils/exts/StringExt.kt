package com.cbmoney.utils.exts

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.cbmoney.domain.model.CategoryType
import java.text.NumberFormat
import java.util.Locale

fun String.formatMoney(): String {
    return try {
        val locale = Locale.forLanguageTag("vi-VN")
        NumberFormat
            .getInstance(locale)
            .format(this.toLong())
    } catch (e: Exception) {
        this
    }
}
fun String.toCategoryType(): CategoryType {
    return CategoryType.entries
        .firstOrNull { it.name.lowercase() == this.lowercase() }
        ?: CategoryType.EXPENSE
}
fun String.hexToColor(): Color {
    return Color(this.toColorInt())
}
fun String.formatDigit(): Long? {
    val clean = this
        .replace(".", "")
    if (clean.all { it.isDigit() } && clean.isNotEmpty()) {
       return clean.toLong()
    }
    return null
}
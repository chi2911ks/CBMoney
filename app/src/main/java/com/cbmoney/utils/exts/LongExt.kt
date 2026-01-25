package com.cbmoney.utils.exts


import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.formatMoney(): String {
    return try {
        NumberFormat.getInstance(Locale("vi", "VN")).format(this)
    } catch (e: Exception) {
        this.toString()
    }

}

fun Long.toFormatDate(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale("vi", "VN"))
    val formatted = formatter.format(date)
    return formatted
}
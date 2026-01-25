package com.cbmoney.utils.exts

import java.text.NumberFormat
import java.util.Locale

fun String.formatMoney(): String {
    return try {
        NumberFormat.getInstance(Locale("vi", "VN")).format(this.toLong())
    } catch (e: Exception) {
        this
    }

}
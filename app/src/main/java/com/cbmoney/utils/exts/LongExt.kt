package com.cbmoney.utils.exts

import java.text.NumberFormat
import java.util.Locale

fun Long.formatMoney(): String =
    NumberFormat.getInstance(Locale("vi", "VN")).format(this)

package com.cbmoney.utils.exts


import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.formatMoney(): String {
    return this.toString().formatMoney()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toFormatDate(
    pattern: String = "dd/MM/yyyy"
): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val date = Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val formatted = date.format(formatter)

    return formatted
}

package com.cbmoney.utils.exts


import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.formatMoney(): String {
    return try {
        NumberFormat.getInstance(Locale("vi", "VN")).format(this)
    } catch (e: Exception) {
        this.toString()
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toFormatDate(): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val date = Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val formatted = date.format(formatter)

    return formatted
}

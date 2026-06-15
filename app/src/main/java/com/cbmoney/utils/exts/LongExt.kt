package com.cbmoney.utils.exts


import android.content.Context
import com.cbmoney.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.formatMoney(): String {
    return this.toString().formatMoney()
}

fun Long.toStartOfDay(): Long {
    val zoneId = ZoneId.systemDefault()
    return Instant.ofEpochMilli(this)
        .atZone(zoneId)
        .toLocalDate()
        .atStartOfDay(zoneId)
        .toInstant()
        .toEpochMilli()
}

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

fun Long.toRelativeDateGroup(context: Context): String {
    val zoneId = ZoneId.systemDefault()
    val date = Instant.ofEpochMilli(this).atZone(zoneId).toLocalDate()
    val today = LocalDate.now(zoneId)
    val dateTimeFormatter = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    
    val dateLabel = when (date) {
        today -> context.getString(R.string.str_today)
        today.minusDays(1) -> context.getString(R.string.str_yesterday)
        else -> {
            val resId = when (date.dayOfWeek.value) {
                1 -> R.string.str_monday
                2 -> R.string.str_tuesday
                3 -> R.string.str_wednesday
                4 -> R.string.str_thursday
                5 -> R.string.str_friday
                6 -> R.string.str_saturday
                7 -> R.string.str_sunday
                else -> -1
            }
            if (resId != -1) context.getString(resId) else ""
        }
    }
    return if (dateLabel.isNotEmpty()) "$dateLabel, $dateTimeFormatter" else dateTimeFormatter
}

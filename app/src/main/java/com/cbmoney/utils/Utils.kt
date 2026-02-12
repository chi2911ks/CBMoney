package com.cbmoney.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import androidx.room.TypeConverter
import com.cbmoney.domain.model.CategoryType

@TypeConverter
fun toPeriod(value: String) = CategoryType.valueOf(value)

@TypeConverter
fun fromPeriod(period: CategoryType) = period.name.lowercase()

fun getStringRes(context: Context, @StringRes resId: Int): String {
    return context.getString(resId)
}

@SuppressLint("DefaultLocale")
fun formatShortNumber(value: Long): String {
    return when {
        value >= 1_000_000 -> {
            val v = value / 1_000_000.0
            if (v % 1 == 0.0) "${v.toInt()}m" else String.format("%.1fm", v)
        }
        value >= 1_000 -> {
            val v = value / 1_000.0
            if (v % 1 == 0.0) "${v.toInt()}k" else String.format("%.1fk", v)
        }
        else -> value.toString()
    }
}

package com.cbmoney.utils

import androidx.room.TypeConverter
import com.cbmoney.domain.model.CategoryType

@TypeConverter
fun toPeriod(value: String) = CategoryType.valueOf(value)

@TypeConverter
fun fromPeriod(period: CategoryType) = period.name.lowercase()




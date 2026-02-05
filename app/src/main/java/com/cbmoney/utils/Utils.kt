package com.cbmoney.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.cbmoney.domain.model.CategoryType
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
fun getYearMonthFormat(
    yearMonth: YearMonth = YearMonth.now(),
    isStartAndEndDate: Boolean = true
): String{
    val start = yearMonth.atDay(1)
    val end = yearMonth.atEndOfMonth()
    return if (isStartAndEndDate)
        "%02d/%d (%02d/%02d - %02d/%02d)".format(
            yearMonth.monthValue,
            yearMonth.year,
            start.dayOfMonth,
            start.monthValue,
            end.dayOfMonth,
            end.monthValue
        )
    else
        "%02d/%d".format(yearMonth.monthValue, yearMonth.year)
}
@TypeConverter
fun toPeriod(value: String) = CategoryType.valueOf(value)

@TypeConverter
fun fromPeriod(period: CategoryType) = period.name

package com.cbmoney.utils

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.YearMonth

object DateUtils {
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
    fun getMonthRange(year: Int, month: Int): Pair<Long, Long> {
        val calendar = Calendar.getInstance()

        // start of month
        calendar.set(year, month - 1, 1, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val start = calendar.timeInMillis

        // end of month
        calendar.add(Calendar.MONTH, 1)
        val end = calendar.timeInMillis

        return start to end
    }

}
package com.cbmoney.data.local.room.relation

import androidx.room.ColumnInfo

data class TotalExpenseAndIncome(
    @ColumnInfo(name = "total_expense")
    val totalExpense: Long,
    @ColumnInfo(name = "total_income")
    val totalIncome: Long
)
package com.cbmoney.data.local.room.relation

import androidx.room.ColumnInfo

data class CategoryWithSpending(
    @ColumnInfo(name = "categoryId")
    val categoryId: String?,
    @ColumnInfo(name = "categoryName")
    val categoryName: String?,
    @ColumnInfo(name = "categoryIcon")
    val categoryIcon: String?,
    @ColumnInfo(name = "iconColor")
    val iconColor: String?,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "countTransaction")
    val countTransaction: Long,
    @ColumnInfo(name = "totalSpending")
    val totalSpending: Long
)

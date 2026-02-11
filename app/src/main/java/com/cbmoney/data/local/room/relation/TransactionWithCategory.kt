package com.cbmoney.data.local.room.relation

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.cbmoney.data.local.room.entities.TransactionEntity

data class TransactionWithCategory(
    @Embedded
    val transaction: TransactionEntity,
    @ColumnInfo(name = "categoryName")
    val categoryName: String?,
    @ColumnInfo(name = "categoryIcon")
    val categoryIcon: String?,
    @ColumnInfo(name = "iconColor")
    val iconColor: String?,
)
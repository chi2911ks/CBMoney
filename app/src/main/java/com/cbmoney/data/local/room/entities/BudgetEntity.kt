package com.cbmoney.data.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "budgets",
    indices = [
        Index(value = ["userId"]),
        Index(value = ["categoryId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BudgetEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val categoryId: String?=null,
    val amount: Double,
    val period: String,  // "monthly" or "yearly"
    val month: String,   // "2024-01"
    val spent: Double, // Tiền đã tiêu
    val createdAt: Long,
    val updatedAt: Long,
)

data class BudgetCategoryEntity(
    @Embedded
    val budget: BudgetEntity,
    @ColumnInfo(name = "categoryName")
    val categoryName: String?,
    @ColumnInfo(name = "categoryIcon")
    val categoryIcon: String?,
    @ColumnInfo(name = "iconColor")
    val iconColor: String?,
)
package com.cbmoney.data.local.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "budgets",
    indices = [
        Index(value = ["userId"])
    ]
)
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val userId: String,
    val categoryId: String,
    val amount: Double,
    val period: String,  // "monthly" or "yearly"
    val month: String,   // "2024-01"
    val spent: Double,
    val createdAt: Long,
    val updatedAt: Long,

)
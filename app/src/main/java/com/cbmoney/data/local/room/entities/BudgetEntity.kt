package com.cbmoney.data.local.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "budgets",
    indices = [
        Index(value = ["userId"])
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
){
    val remaining: Double
        get() = amount - spent

    val percentage: Float
        get() = if (amount > 0) (spent / amount * 100).toFloat() else 0f

    val isOverBudget: Boolean
        get() = spent > amount

    val isNearLimit: Boolean
        get() = percentage >= 80f
}


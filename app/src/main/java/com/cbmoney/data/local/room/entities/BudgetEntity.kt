package com.cbmoney.data.local.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


/**
 * Represents a budget entry in the local database.
 *
 * This entity tracks the allocated budget for a specific category or user over a
 * defined period (e.g., monthly or yearly) and monitors the current spending
 * against that budget.
 *
 * @property id Unique identifier for the budget record.
 * @property userId The ID of the user who owns this budget.
 * @property categoryId The ID of the category associated with this budget.
 * If null, the budget may apply to overall spending.
 * @property amount The total monetary limit allocated for this budget.
 * @property period The frequency of the budget, typically "monthly" or "yearly".
 * @property month The specific time frame for the budget, formatted as "YYYY-MM".
 * @property spent The actual amount of money spent within this budget period so far.
 * @property createdAt The timestamp indicating when the budget record was created.
 * @property updatedAt The timestamp indicating the last time the budget record was modified.
 */
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


package com.cbmoney.data.local.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    indices = [Index(value = ["userId"])]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val userId: String,
    val amount: Double,
    val type: String,  // "expense" or "income"
    val categoryId: String,
    val categoryName: String,  // Denormalized
    val description: String,
    val date: Long,
    val createdAt: Long,
    val updatedAt: Long,

)
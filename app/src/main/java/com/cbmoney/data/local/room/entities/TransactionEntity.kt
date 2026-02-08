package com.cbmoney.data.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    indices = [
        Index(value = ["userId"]),
        Index(value = ["categoryId"])
              ],

    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val amount: Long,
    val type: String,  // "expense" or "income"
    val categoryId: String? = null,
    val description: String,
    val date: Long,
    val createdAt: Long,
    val updatedAt: Long
)
data class TransactionCategoryEntity(
    @Embedded
    val transaction: TransactionEntity,
    @ColumnInfo(name = "categoryName")
    val categoryName: String?,
    @ColumnInfo(name = "categoryIcon")
    val categoryIcon: String?,
    @ColumnInfo(name = "iconColor")
    val iconColor: String?,
)

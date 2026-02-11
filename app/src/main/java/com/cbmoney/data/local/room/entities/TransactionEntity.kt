package com.cbmoney.data.local.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents a financial transaction record stored in the local database.
 *
 * This entity tracks both income and expenses associated with a specific user and category.
 * It maintains a foreign key relationship with [CategoryEntity].
 *
 * @property id The unique identifier for the transaction.
 * @property userId The identifier of the user who owns this transaction.
 * @property amount The monetary value of the transaction (stored as a Long, typically in the smallest currency unit).
 * @property type The classification of the transaction, restricted to "expense" or "income".
 * @property categoryId The identifier of the associated category, null if the category was deleted or not assigned.
 * @property description A brief note or memo describing the transaction.
 * @property date The timestamp indicating when the actual transaction occurred.
 * @property createdAt The timestamp indicating when the record was first created in the database.
 * @property updatedAt The timestamp indicating when the record was last modified.
 */
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



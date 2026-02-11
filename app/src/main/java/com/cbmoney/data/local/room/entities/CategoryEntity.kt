package com.cbmoney.data.local.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents a transaction category stored in the local Room database.
 *
 * @property id The unique identifier for the category.
 * @property userId The ID of the user who owns this category, or null if it is a system-wide category.
 * @property name The display name of the category (e.g., "Food", "Transport").
 * @property type The type of category, typically distinguishing between income and expense.
 * @property icon A string identifier or resource name for the category's visual icon.
 * @property color A hex code or color identifier used for UI representation.
 * @property order The sorting priority used when displaying categories in a list.
 * @property isDefault Indicates whether the category is a pre-defined system default.
 * @property createdAt The timestamp representing when the category was created.
 */
@Entity(
    tableName = "categories",
    indices = [
        Index(value = ["userId"])
    ]
)
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val userId: String? = null,
    val name: String,
    val type: String,
    val icon: String,
    val color: String,
    val order: Int,
    val isDefault: Boolean = true,
    val createdAt: Long
)

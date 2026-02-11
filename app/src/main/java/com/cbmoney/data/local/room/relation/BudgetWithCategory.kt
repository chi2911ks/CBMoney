package com.cbmoney.data.local.room.relation

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.cbmoney.data.local.room.entities.BudgetEntity

/**
 * Data class representing a budget combined with its associated category information.
 * This class is typically used for database queries involving a join between the budget table
 * and the categories table to provide descriptive information alongside budget details.
 *
 * @property budget The base budget entity containing core budget data.
 * @property categoryName The display name of the associated category.
 * @property categoryIcon The resource identifier or name for the category icon.
 * @property iconColor The color hex code or resource name for the category icon.
 */
data class BudgetWithCategory(
    @Embedded
    val budget: BudgetEntity,
    @ColumnInfo(name = "categoryName")
    val categoryName: String?,
    @ColumnInfo(name = "categoryIcon")
    val categoryIcon: String?,
    @ColumnInfo(name = "iconColor")
    val iconColor: String?,
)
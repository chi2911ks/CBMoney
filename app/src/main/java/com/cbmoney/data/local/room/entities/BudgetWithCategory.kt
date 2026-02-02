package com.cbmoney.data.local.room.entities

import androidx.room.Embedded
import androidx.room.Relation

data class BudgetWithCategory(
    @Embedded val budget: BudgetEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity
)
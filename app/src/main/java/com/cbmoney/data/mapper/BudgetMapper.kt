package com.cbmoney.data.mapper

import com.cbmoney.data.local.room.entities.BudgetCategoryEntity
import com.cbmoney.data.local.room.entities.BudgetEntity
import com.cbmoney.domain.model.Budget
import com.cbmoney.domain.model.BudgetCategory

fun BudgetEntity.toDomain(): Budget = Budget(
    id = id,
    userId = userId,
    categoryId = categoryId,
    amount = amount.toLong(),
    period = period,
    month = month,
    spent = spent.toLong(),
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun Budget.toEntity(): BudgetEntity = BudgetEntity(
    id = id,
    userId = userId,
    categoryId = categoryId,
    amount = amount.toDouble(),
    period = period,
    month = month,
    spent = spent.toDouble(),
    createdAt = createdAt,
    updatedAt = updatedAt,
)





fun BudgetCategoryEntity.toDomain(): BudgetCategory = BudgetCategory(
    budget = budget.toDomain(),
    categoryName = categoryName,
    categoryIcon = categoryIcon,
    iconColor = iconColor,
)
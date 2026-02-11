package com.cbmoney.data.mapper


import com.cbmoney.data.local.room.entities.BudgetEntity
import com.cbmoney.domain.model.Budget

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






package com.cbmoney.data.mapper

import com.cbmoney.data.local.room.entities.TransactionEntity
import com.cbmoney.domain.model.Transaction

fun TransactionEntity.toDomain(): Transaction  {
    return Transaction(
        id = id,
        userId = userId,
        amount = amount,
        type = type,
        categoryId = categoryId,
        description = description,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Transaction.toEntity(): TransactionEntity  {
    return TransactionEntity(
        id = id,
        userId = userId,
        amount = amount,
        type = type,
        categoryId = categoryId,
        description = description,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}


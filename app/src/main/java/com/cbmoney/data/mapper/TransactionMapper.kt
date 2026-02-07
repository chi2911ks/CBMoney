package com.cbmoney.data.mapper

import com.cbmoney.data.local.room.entities.TransactionCategoryEntity
import com.cbmoney.data.local.room.entities.TransactionEntity
import com.cbmoney.domain.model.Transaction
import com.cbmoney.domain.model.TransactionCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
fun TransactionCategoryEntity.toDomain(): TransactionCategory {
    return TransactionCategory(
        transaction = transaction.toDomain(),
        categoryName = categoryName,
        categoryIcon = categoryIcon,
        iconColor = iconColor
    )
}
fun Flow<List<TransactionCategoryEntity>>.toDomain(): Flow<List<TransactionCategory>>{
    return this.map {
        it.map { transaction ->
            transaction.toDomain()
        }
    }
}
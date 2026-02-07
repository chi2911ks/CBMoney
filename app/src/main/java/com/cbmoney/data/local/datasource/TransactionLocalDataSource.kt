package com.cbmoney.data.local.datasource

import com.cbmoney.data.local.room.entities.TransactionCategoryEntity
import com.cbmoney.data.local.room.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface TransactionLocalDataSource {

    suspend fun getTransactionsByDateRange(
        userId: String,
        categoryId: String,
        startDate: Long,
        endDate: Long
    ): List<TransactionEntity>

    suspend fun upsertTransaction(transaction: TransactionEntity)
    suspend fun deleteTransaction(transaction: TransactionEntity)

    fun getAllTransactions(userId: String): Flow<List<TransactionEntity>>
    fun getRecentTransactions(userId: String, limit: Int): Flow<List<TransactionCategoryEntity>>


}
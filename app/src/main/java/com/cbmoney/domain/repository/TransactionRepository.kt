package com.cbmoney.domain.repository

import com.cbmoney.domain.model.Transaction
import com.cbmoney.domain.model.TransactionCategory
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getTransactionsByDateRange(
        categoryId: String,
        startDate: Long,
        endDate: Long
    ): List<Transaction>
    suspend fun upsertTransaction(transaction: Transaction): Result<Boolean>
    suspend fun deleteTransaction(transaction: Transaction): Result<Boolean>
    fun getAllTransactions(): Flow<List<Transaction>>
    fun getRecentTransactions(limit: Int): Flow<List<TransactionCategory>>
}
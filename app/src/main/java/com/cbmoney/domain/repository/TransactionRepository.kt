package com.cbmoney.domain.repository

import com.cbmoney.domain.model.CategorySpending
import com.cbmoney.domain.model.FinancialSummary
import com.cbmoney.domain.model.Transaction
import com.cbmoney.domain.model.TransactionDetails
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
    fun getRecentTransactions(limit: Int): Flow<List<TransactionDetails>>
    fun getCategorySpending(
        startDate: Long,
        endDate: Long
    ): Flow<List<CategorySpending>>
    fun getTotalExpenseAndIncome(
        startDate: Long,
        endDate: Long
    ): Flow<FinancialSummary>
}
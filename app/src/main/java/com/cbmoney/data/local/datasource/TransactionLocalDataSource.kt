package com.cbmoney.data.local.datasource

import com.cbmoney.data.local.room.entities.TransactionEntity
import com.cbmoney.data.local.room.relation.CategoryWithSpending
import com.cbmoney.data.local.room.relation.TotalExpenseAndIncome
import com.cbmoney.data.local.room.relation.TransactionWithCategory
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
    fun getRecentTransactions(userId: String, limit: Int): Flow<List<TransactionWithCategory>>

    fun getCategorySpending(
        userId: String,
        startDate: Long,
        endDate: Long
    ): Flow<List<CategoryWithSpending>>

    fun getTotalExpenseAndIncome(
        userId: String,
        startDate: Long,
        endDate: Long
    ): Flow<TotalExpenseAndIncome>
}
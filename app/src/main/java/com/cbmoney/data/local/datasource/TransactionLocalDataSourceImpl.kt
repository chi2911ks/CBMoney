package com.cbmoney.data.local.datasource

import com.cbmoney.data.local.room.dao.TransactionDao
import com.cbmoney.data.local.room.entities.TransactionEntity
import com.cbmoney.data.local.room.relation.CategoryWithSpending
import com.cbmoney.data.local.room.relation.TotalExpenseAndIncome
import com.cbmoney.data.local.room.relation.TransactionWithCategory
import kotlinx.coroutines.flow.Flow

class TransactionLocalDataSourceImpl(
    private val transactionDao: TransactionDao
): TransactionLocalDataSource {
    override suspend fun getTransactionsByDateRange(
        userId: String,
        categoryId: String,
        startDate: Long,
        endDate: Long
    ): List<TransactionEntity> {
        return transactionDao.getTransactionsByDateRange(userId, categoryId, startDate, endDate)
    }

    override suspend fun upsertTransaction(transaction: TransactionEntity) {
        return transactionDao.upsert(transaction)
    }

    override suspend fun deleteTransaction(transaction: TransactionEntity) {
        return transactionDao.delete(transaction)
    }

    override fun getAllTransactions(userId: String): Flow<List<TransactionEntity>> {
        return transactionDao.getAllTransactions(userId)
    }

    override fun getRecentTransactions(
        userId: String,
        limit: Int
    ): Flow<List<TransactionWithCategory>> {
        return transactionDao.getRecentTransactions(userId, limit)
    }

    override fun getCategorySpending(
        userId: String,
        startDate: Long,
        endDate: Long
    ): Flow<List<CategoryWithSpending>> {
        return transactionDao.getCategorySpending(userId, startDate, endDate)
    }

    override fun getTotalExpenseAndIncome(
        userId: String,
        startDate: Long,
        endDate: Long
    ): Flow<TotalExpenseAndIncome> {
        return transactionDao.getTotalExpenseAndIncome(userId, startDate, endDate)
    }

    override fun getMonthlySpending(
        userId: String,
        startDate: Long,
        endDate: Long
    ): Flow<List<TotalExpenseAndIncome>> {
        return transactionDao.getMonthlySpending(userId, startDate, endDate)
    }

}
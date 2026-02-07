package com.cbmoney.data.local.datasource

import com.cbmoney.data.local.room.dao.TransactionDao
import com.cbmoney.data.local.room.entities.TransactionCategoryEntity
import com.cbmoney.data.local.room.entities.TransactionEntity
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
    ): Flow<List<TransactionCategoryEntity>> {
        return transactionDao.getRecentTransactions(userId, limit)
    }

}
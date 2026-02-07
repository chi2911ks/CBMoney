package com.cbmoney.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.cbmoney.base.BaseDao
import com.cbmoney.data.local.room.entities.TransactionCategoryEntity
import com.cbmoney.data.local.room.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao: BaseDao<TransactionEntity> {
    @Query("""
        SELECT * 
        FROM transactions 
        WHERE userId = :userId AND
        categoryId = :categoryId  AND 
        date >= :startDate AND date < :endDate
        ORDER BY date DESC
    """)
    suspend fun getTransactionsByDateRange(
        userId: String,
        categoryId: String,
        startDate: Long,
        endDate: Long): List<TransactionEntity>
    @Query("SELECT * FROM transactions WHERE id = :userId")
    fun getAllTransactions(userId: String): Flow<List<TransactionEntity>>

    @Query("""
        SELECT t.*,
            c.name AS categoryName,
            c.icon AS categoryIcon,
            c.color AS iconColor
        FROM transactions as t
        LEFT JOIN categories as c ON t.categoryId = c.id
        WHERE t.userId = :userId 
        ORDER BY `date` DESC 
        LIMIT :limit
        """)
    fun getRecentTransactions(userId: String, limit: Int): Flow<List<TransactionCategoryEntity>>
}
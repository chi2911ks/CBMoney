package com.cbmoney.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.cbmoney.base.BaseDao
import com.cbmoney.data.local.room.entities.TransactionEntity
import com.cbmoney.data.local.room.relation.CategoryWithSpending
import com.cbmoney.data.local.room.relation.TotalExpenseAndIncome
import com.cbmoney.data.local.room.relation.TransactionWithCategory
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
    fun getRecentTransactions(userId: String, limit: Int): Flow<List<TransactionWithCategory>>

    @Query("""
        SELECT
            c.id AS categoryId,
            c.name AS categoryName,
            c.icon AS categoryIcon,
            c.color AS iconColor,
            t.type AS type,
            COUNT(t.id) as countTransaction,
            SUM(t.amount) as totalSpending
        FROM transactions as t
        LEFT JOIN categories as c ON t.categoryId = c.id
        WHERE t.userId = :userId AND
        date >= :startDate AND date < :endDate
        GROUP BY categoryId
        
        """)
    fun getCategorySpending(
        userId: String,
        startDate: Long,
        endDate: Long
    ): Flow<List<CategoryWithSpending>>

    @Query("""
        SELECT 
            SUM(CASE WHEN type = 'expense' THEN amount ELSE 0 END) AS total_expense,
            SUM(CASE WHEN type = 'income' THEN amount ELSE 0 END) AS total_income
        FROM transactions 
        WHERE userId = :userId AND
        date >= :startDate AND date < :endDate
    """)
    fun getTotalExpenseAndIncome(
        userId: String,
        startDate: Long,
        endDate: Long
    ): Flow<TotalExpenseAndIncome>


}
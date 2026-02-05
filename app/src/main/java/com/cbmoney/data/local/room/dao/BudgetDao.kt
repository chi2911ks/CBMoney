package com.cbmoney.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.cbmoney.base.BaseDao
import com.cbmoney.data.local.room.entities.BudgetCategoryEntity
import com.cbmoney.data.local.room.entities.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao: BaseDao<BudgetEntity> {
    @Query("SELECT * FROM budgets WHERE userId = :userId")
    fun getAllBudgets(userId: String): Flow<List<BudgetEntity>>
    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudgetById(id: String): BudgetEntity

    @Query("""
        SELECT * 
        FROM budgets 
        WHERE userId = :userId AND 
        (month = :month) AND 
        categoryId IS NULL
    """)
    suspend fun getTotalBudgetByMonth(userId: String, month: String): BudgetEntity


    @Query("""
        SELECT 
            b.*,
            c.name AS categoryName,
            c.icon AS categoryIcon,
            c.color AS iconColor
        FROM budgets as b
        LEFT JOIN categories as c ON b.categoryId = c.id
        WHERE b.userId = :userId AND b.month = :month
    """)
    fun getBudgetsCategoryByMonth(userId: String, month: String): Flow<List<BudgetCategoryEntity>>
    @Query("SELECT COUNT(*) FROM budgets WHERE userId = :userId AND month = :month")
    suspend fun countBudgetsByMonth(userId: String, month: String): Int
    @Query("SELECT * FROM budgets WHERE userId = :userId AND month = :month")
    fun getBudgetsByMonth(userId: String, month: String): Flow<List<BudgetEntity>>

}
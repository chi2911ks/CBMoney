package com.cbmoney.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.cbmoney.base.BaseDao
import com.cbmoney.data.local.room.entities.CategoryEntity
import com.cbmoney.data.local.room.entities.CategorySpendingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao: BaseDao<CategoryEntity> {
    @Query("SELECT * FROM categories WHERE userId = :userId or userId is null order by `order` ASC")
    fun getAllCategories(userId: String): Flow<List<CategoryEntity>>
    @Query("SELECT COUNT(*) FROM categories")
    suspend fun countCategories(): Int
    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: String): CategoryEntity

    @Query("SELECT * FROM categories WHERE (userId = :userId or userId is null) and type = :type and icon != 'more_horiz' ORDER BY `order` ASC")
    fun getCategoriesByType(userId: String, type: String): Flow<List<CategoryEntity>>
    @Query("DELETE FROM categories")
    suspend fun deleteAll()
    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun delete(id: String)
    @Query("SELECT MAX(`order`) FROM categories WHERE type = :type and icon != 'more_horiz'")
    suspend fun getLastOrder(type: String): Int
    @Query("""
        SELECT 
            c.id as categoryId, 
            c.name, 
            c.icon, 
            c.color as iconColor,
            COUNT(t.id) as countTransaction,
            SUM(t.amount) as amount
        FROM categories as c
        LEFT JOIN transactions as t ON c.id = t.categoryId
        WHERE t.userId = :userId AND
        t.date >= :startDate AND t.date < :endDate
        GROUP BY c.id
        ORDER BY `amount` ASC

    """)
   fun getCategoriesSpending(
       userId: String,
       startDate: Long,
       endDate: Long
   ): Flow<List<CategorySpendingEntity>>

}
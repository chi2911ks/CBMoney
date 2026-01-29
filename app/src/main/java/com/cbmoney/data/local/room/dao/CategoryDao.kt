package com.cbmoney.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.cbmoney.base.BaseDao
import com.cbmoney.data.local.room.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao: BaseDao<CategoryEntity> {
    @Query("SELECT * FROM categories WHERE userId = :userId or userId = '' or userId is null order by `order` ASC")
    fun getAllCategories(userId: String): Flow<List<CategoryEntity>>
    @Query("SELECT COUNT(*) FROM categories")
    suspend fun countCategories(): Int
    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: String): CategoryEntity
    @Query("DELETE FROM categories")
    suspend fun deleteAll()
    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun delete(id: String)
    @Query("SELECT MAX(`order`) FROM categories WHERE type = :type and icon != 'more_horiz'")
    suspend fun getLastOrder(type: String): Int

}
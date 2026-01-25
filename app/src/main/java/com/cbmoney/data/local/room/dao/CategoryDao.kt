package com.cbmoney.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cbmoney.data.local.room.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao{
    @Insert
    suspend fun insertCategory(categoryEntity: CategoryEntity)
    @Insert
    suspend fun insertCategories(categories: List<CategoryEntity>)
    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity)
    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): Flow<List<CategoryEntity>>
    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: String): CategoryEntity
}
package com.cbmoney.data.local.datasource

import com.cbmoney.data.local.room.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryLocalDataSource {
    fun getAllCategories(userId: String): Flow<List<CategoryEntity>>
    suspend fun countCategories(): Int
    suspend fun insertCategory(category: CategoryEntity)
    suspend fun insertCategories(categories: List<CategoryEntity>)
    suspend fun deleteCategory(categoryId: String)
    suspend fun updateCategory(category: CategoryEntity)
    suspend fun getLastOrder(type: String): Int
}
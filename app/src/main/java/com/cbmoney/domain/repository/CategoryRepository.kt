package com.cbmoney.domain.repository

import com.cbmoney.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun syncCategories(uid: String): Result<Boolean>
    suspend fun initCategoriesDefault(): Result<Boolean>
    fun getAllCategories(): Flow<List<Category>>
    fun getCategoriesByType(type: String): Flow<List<Category>>
    suspend fun deleteCategory(categoryId: String): Result<Boolean>
    suspend fun addCategory(category: Category): Result<Boolean>
    suspend fun updateCategory(category: Category): Result<Boolean>

}

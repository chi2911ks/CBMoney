package com.cbmoney.domain.repository

import com.cbmoney.domain.model.Category
import com.cbmoney.domain.model.CategoryType
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun syncCategories(uid: String): Result<Boolean>
    suspend fun initCategoriesDefault(): Result<Boolean>
    fun getAllCategories(): Flow<List<Category>>
    fun getCategoriesByType(type: CategoryType): Flow<List<Category>>
    suspend fun deleteCategory(categoryId: String): Result<Boolean>
    suspend fun addCategory(category: Category): Result<Boolean>
    suspend fun updateCategory(category: Category): Result<Boolean>
    suspend fun upsertCategory(category: Category): Result<Boolean>

}

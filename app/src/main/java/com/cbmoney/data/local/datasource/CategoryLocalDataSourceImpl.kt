package com.cbmoney.data.local.datasource

import com.cbmoney.data.local.room.dao.CategoryDao
import com.cbmoney.data.local.room.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

class CategoryLocalDataSourceImpl(
    private val categoryDao: CategoryDao
): CategoryLocalDataSource  {
    override fun getAllCategories(userId: String): Flow<List<CategoryEntity>> {
        return categoryDao.getAllCategories(userId)
    }

    override suspend fun countCategories(): Int {
        return categoryDao.countCategories()
    }

    override suspend fun insertCategory(category: CategoryEntity) {
        categoryDao.insert(category)
    }

    override suspend fun insertCategories(categories: List<CategoryEntity>) {
        categoryDao.insert(categories)
    }

    override suspend fun deleteCategory(categoryId: String) {
        categoryDao.delete(categoryId)
    }

    override suspend fun updateCategory(category: CategoryEntity) {
        categoryDao.update(category)
    }
    override suspend fun getLastOrder(type: String): Int {
        return categoryDao.getLastOrder(type)
    }
}
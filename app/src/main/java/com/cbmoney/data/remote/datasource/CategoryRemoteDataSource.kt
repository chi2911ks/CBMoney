package com.cbmoney.data.remote.datasource

import com.cbmoney.data.remote.model.CategoryFirestore

interface CategoryRemoteDataSource{
    suspend fun fetchAllCategories(): List<CategoryFirestore>
    suspend fun fetchCategoriesByType(type: String): List<CategoryFirestore>
    suspend fun saveCategory(category: CategoryFirestore)
    suspend fun initCategoriesDefault(uid: String)

}
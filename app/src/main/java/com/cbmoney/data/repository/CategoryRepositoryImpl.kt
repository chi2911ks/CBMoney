package com.cbmoney.data.repository

import android.util.Log
import com.cbmoney.data.local.datasource.CategoryLocalDataSource
import com.cbmoney.data.mapper.toDomain
import com.cbmoney.data.mapper.toEntity
import com.cbmoney.data.remote.datasource.CategoryRemoteDataSource
import com.cbmoney.domain.constants.DefaultCategories
import com.cbmoney.domain.model.Category
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.domain.repository.CategoryRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class CategoryRepositoryImpl(
    private val categoryLocalDataSource: CategoryLocalDataSource,
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val firebaseAuth: FirebaseAuth
) : CategoryRepository {
    private val userId get() = firebaseAuth.currentUser!!.uid

    override suspend fun syncCategories(uid: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun initCategoriesDefault(): Result<Boolean> {
        return try {
            val count = categoryLocalDataSource.countCategories()

            if (count == 0) {
                categoryLocalDataSource.insertCategories(
                    DefaultCategories.ALL_CATEGORIES.map {
                        it.toEntity().copy(
                            id = UUID.randomUUID().toString(),
                            createdAt = System.currentTimeMillis()
                        )
                    }
                )
                return Result.success(true)
            }
            Result.success(true)
        } catch (e: Exception) {
            Log.w(TAG, "initCategoriesDefault: ${e}")
            return Result.failure(e)
        }


    }

    override fun getAllCategories(): Flow<List<Category>> {
        return try {
            categoryLocalDataSource.getAllCategories(userId).map {
                it.map { categoryEntity ->
                    categoryEntity.toDomain()
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "getAllCategories: ${e}")
            flow { emptyList<Category>() }
        }
    }
//    KsiFLX54gEV9CqT4LZIsaBW2t3u1
    override fun getCategoriesByType(type: CategoryType): Flow<List<Category>> {
        return try {
            categoryLocalDataSource.getCategoriesByType(userId, type.name.lowercase()).map { categories ->
                categories.map { categoryEntity -> categoryEntity.toDomain() }
            }
        } catch (e: Exception) {
            Log.w(TAG, "getCategoriesByType: ${e}")
            flow { emptyList<Category>() }
        }
    }

    override suspend fun deleteCategory(categoryId: String): Result<Boolean> {
        return try {
            categoryLocalDataSource.deleteCategory(categoryId)
            Result.success(true)
        } catch (e: Exception) {
            Log.w(TAG, "deleteCategory: ${e}")
            Result.failure(e)
        }

    }

    override suspend fun addCategory(category: Category): Result<Boolean> {
        return try {
            val lastOrder = categoryLocalDataSource.getLastOrder(category.type.name.lowercase())
            categoryLocalDataSource.insertCategory(
                category.toEntity().copy(
                    id = UUID.randomUUID().toString(),
                    order = lastOrder + 1,
                    createdAt = System.currentTimeMillis(),
                )
            )
            Result.success(true)
        } catch (e: Exception) {
            Log.w(TAG, "saveCategory: ${e}")
            Result.failure(e)
        }
    }

    override suspend fun updateCategory(category: Category): Result<Boolean> {
        return try {
            categoryLocalDataSource.updateCategory(category.toEntity())
            Result.success(true)
        } catch (e: Exception) {
            Log.w(TAG, "updateCategory: ${e}")
            Result.failure(e)
        }
    }

    override suspend fun upsertCategory(category: Category): Result<Boolean> {
        return try {
            categoryLocalDataSource.upsertCategory(category.toEntity())
            Result.success(true)
        } catch (e: Exception) {
            Log.w(TAG, "upsertCategory: ${e}")
            Result.failure(e)
        }

    }


    companion object {
        private const val TAG = "CategoryRepositoryImpl"
    }
}
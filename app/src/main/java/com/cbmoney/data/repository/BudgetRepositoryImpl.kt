package com.cbmoney.data.repository

import android.util.Log
import com.cbmoney.data.local.datasource.BudgetLocalDataSource
import com.cbmoney.data.mapper.toDomain
import com.cbmoney.data.mapper.toEntity
import com.cbmoney.domain.model.Budget
import com.cbmoney.domain.model.BudgetCategory
import com.cbmoney.domain.repository.BudgetRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class BudgetRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val budgetLocalDataSource: BudgetLocalDataSource
): BudgetRepository {
    private val userId get() = firebaseAuth.currentUser!!.uid



    override suspend fun upsertBudgets(budgets: List<Budget>): Result<Boolean> {
        return try {
            budgetLocalDataSource.upsertBudgets(budgets.map {
                it.toEntity().copy(
                    id = it.id.ifEmpty { UUID.randomUUID().toString() },
                    userId = userId
                )
            })
            Result.success(true)
        }catch (e: Exception){
            Log.w(TAG, "upsertBudgets: $e")
            Result.failure(e)
        }
    }

    override fun getBudgetsCategoryByMonth(month: String): Flow<List<BudgetCategory>> {
        return try {
            budgetLocalDataSource.getBudgetsCategoryByMonth(userId, month).map {
                it.map { entity -> entity.toDomain() }
            }
        }catch (e: Exception){
            Log.w(TAG, "getBudgetsCategoryByMonth: $e")
            flow {emit(emptyList())}
        }

    }

    override fun getBudgetsByMonth(month: String): Flow<List<Budget>> {
        return try {
            budgetLocalDataSource.getBudgetsByMonth(userId, month).map {
                it.map { entity -> entity.toDomain() }
            }
        }catch (e: Exception){
            Log.w(TAG, "getBudgetsByMonth: $e")
            flow {emit(emptyList())}
        }
    }

    override suspend fun checkBudgetByCategoryExists(
        month: String,
        categoryId: String
    ): Result<Boolean> {
        return try {
            budgetLocalDataSource.getBudgetByCategory(userId, month, categoryId)?.let {
                Result.success(true)
            } ?: Result.success(false)
        }catch (e: Exception){
            Log.w(TAG, "checkBudgetByCategoryExists: $e")
            Result.failure(e)
        }

    }

    override suspend fun checkBudgetTotalExists(month: String): Result<Boolean> {
        return try {
            budgetLocalDataSource.getTotalBudgetByMonth(userId, month)?.let {
                Result.success(true)
            } ?: Result.success(false)
        }
        catch (e: Exception){
            Log.w(TAG, "checkBudgetTotalExists: $e")
            Result.failure(e)
        }
    }


    override suspend fun updateSpent(categoryId: String, month: String, spent: Long): Result<Boolean> {
        return try {
            budgetLocalDataSource.updateSpentTotal(userId, spent, month)
            budgetLocalDataSource.updateSpent(userId, categoryId, spent, month)
            Result.success(true)
        }catch (e: Exception){
            Log.w(TAG, "updateBudget: $e")
            Result.failure(e)
        }
    }


    companion object{
        private const val TAG = "BudgetRepositoryImpl"
    }
}
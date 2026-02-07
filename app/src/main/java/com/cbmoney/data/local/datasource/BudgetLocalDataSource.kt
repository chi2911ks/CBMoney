package com.cbmoney.data.local.datasource

import com.cbmoney.data.local.room.entities.BudgetCategoryEntity
import com.cbmoney.data.local.room.entities.BudgetEntity
import kotlinx.coroutines.flow.Flow

interface BudgetLocalDataSource {
    fun getAllBudgets(userId: String): Flow<List<BudgetEntity>>

    suspend fun getBudgetByCategory(userId: String, month: String, categoryId: String): BudgetEntity?
    suspend fun getTotalBudgetByMonth(userId: String, month: String): BudgetEntity?

    fun getBudgetsCategoryByMonth(userId: String, month: String): Flow<List<BudgetCategoryEntity>>

    fun getBudgetsByMonth(userId: String, month: String): Flow<List<BudgetEntity>>

    suspend fun insertBudget(budget: BudgetEntity): Long
    suspend fun updateBudget(budget: BudgetEntity): Int
    suspend fun deleteBudget(budget: BudgetEntity)

    suspend fun upsertBudget(budget: BudgetEntity)
    suspend fun upsertBudgets(budgets: List<BudgetEntity>)

    suspend fun updateSpent(userId: String, categoryId: String, spent: Long, month: String)
    suspend fun updateSpentTotal(userId: String, spent: Long, month: String)

}
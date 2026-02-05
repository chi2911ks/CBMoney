package com.cbmoney.data.local.datasource

import com.cbmoney.data.local.room.entities.BudgetCategoryEntity
import com.cbmoney.data.local.room.entities.BudgetEntity
import kotlinx.coroutines.flow.Flow

interface BudgetLocalDataSource {
    fun getAllBudgets(userId: String): Flow<List<BudgetEntity>>
    suspend fun getBudgetById(id: String): BudgetEntity
    suspend fun countBudgetsByMonth(userId: String, month: String): Int

    fun getBudgetsCategoryByMonth(userId: String, month: String): Flow<List<BudgetCategoryEntity>>

    fun getBudgetsByMonth(userId: String, month: String): Flow<List<BudgetEntity>>

    suspend fun insertBudget(budget: BudgetEntity): Long
    suspend fun updateBudget(budget: BudgetEntity): Int
    suspend fun deleteBudget(budget: BudgetEntity)

    suspend fun upsertBudget(budget: BudgetEntity)
    suspend fun upsertBudgets(budgets: List<BudgetEntity>)

}
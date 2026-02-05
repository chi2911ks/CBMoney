package com.cbmoney.domain.repository

import com.cbmoney.domain.model.Budget
import com.cbmoney.domain.model.BudgetCategory
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    suspend fun upsertBudgets(budgets: List<Budget>): Result<Boolean>

    fun getBudgetsCategoryByMonth(month: String): Flow<List<BudgetCategory>>
    fun getBudgetsByMonth(month: String): Flow<List<Budget>>



    suspend fun countBudgetsByMonth(month: String): Int
}
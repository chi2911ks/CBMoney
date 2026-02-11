package com.cbmoney.domain.repository

import com.cbmoney.domain.model.Budget
import com.cbmoney.domain.model.BudgetDetails
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    suspend fun upsertBudgets(budgets: List<Budget>): Result<Boolean>

    fun getBudgetsCategoryByMonth(month: String): Flow<List<BudgetDetails>>
    fun getBudgetsByMonth(month: String): Flow<List<Budget>>
    suspend fun checkBudgetByCategoryExists(month: String, categoryId: String): Result<Boolean>
    suspend fun checkBudgetTotalExists(month: String): Result<Boolean>

    suspend fun updateSpent(categoryId: String, month: String, spent: Long): Result<Boolean>



}
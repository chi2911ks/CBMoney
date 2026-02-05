package com.cbmoney.data.local.datasource

import com.cbmoney.data.local.room.dao.BudgetDao
import com.cbmoney.data.local.room.entities.BudgetCategoryEntity
import com.cbmoney.data.local.room.entities.BudgetEntity
import kotlinx.coroutines.flow.Flow

class BudgetLocalDataSourceImpl(
    private val budgetDao: BudgetDao
): BudgetLocalDataSource {
    override fun getAllBudgets(userId: String): Flow<List<BudgetEntity>> {
        return budgetDao.getAllBudgets(userId)
    }

    override suspend fun getBudgetById(id: String): BudgetEntity {
        return budgetDao.getBudgetById(id)
    }


    override suspend fun countBudgetsByMonth(userId: String, month: String): Int {
        return budgetDao.countBudgetsByMonth(userId, month)
    }

    override fun getBudgetsCategoryByMonth(
        userId: String,
        month: String
    ): Flow<List<BudgetCategoryEntity>> {
        return budgetDao.getBudgetsCategoryByMonth(userId, month)
    }

    override fun getBudgetsByMonth(
        userId: String,
        month: String
    ): Flow<List<BudgetEntity>> {
        return budgetDao.getBudgetsByMonth(userId, month)
    }

    override suspend fun insertBudget(budget: BudgetEntity): Long {
        return budgetDao.insert(budget)
    }

    override suspend fun updateBudget(budget: BudgetEntity): Int {
        return budgetDao.update(budget)
    }

    override suspend fun deleteBudget(budget: BudgetEntity) {
        return budgetDao.delete(budget)
    }

    override suspend fun upsertBudget(budget: BudgetEntity) {
        return budgetDao.upsert(budget)
    }

    override suspend fun upsertBudgets(budgets: List<BudgetEntity>) {
        return budgetDao.upsert(budgets)
    }
}
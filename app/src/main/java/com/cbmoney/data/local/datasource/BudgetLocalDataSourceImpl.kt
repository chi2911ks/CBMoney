package com.cbmoney.data.local.datasource

import com.cbmoney.data.local.room.dao.BudgetDao
import com.cbmoney.data.local.room.entities.BudgetEntity
import com.cbmoney.data.local.room.relation.BudgetWithCategory
import kotlinx.coroutines.flow.Flow

class BudgetLocalDataSourceImpl(
    private val budgetDao: BudgetDao
): BudgetLocalDataSource {
    override fun getAllBudgets(userId: String): Flow<List<BudgetEntity>> {
        return budgetDao.getAllBudgets(userId)
    }

    override suspend fun getBudgetByCategory(
        userId: String,
        month: String,
        categoryId: String
    ): BudgetEntity? {
        return budgetDao.getBudgetByCategory(userId, month, categoryId)
    }

    override suspend fun getTotalBudgetByMonth(
        userId: String,
        month: String
    ): BudgetEntity? {
        return budgetDao.getTotalBudgetByMonth(userId, month)
    }


    override fun getBudgetsCategoryByMonth(
        userId: String,
        month: String
    ): Flow<List<BudgetWithCategory>> {
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

    override suspend fun updateSpent(
        userId: String,
        categoryId: String,
        spent: Long,
        month: String
    ) {
        return budgetDao.updateSpent(userId, categoryId, spent, month)
    }

    override suspend fun updateSpentTotal(
        userId: String,
        spent: Long,
        month: String
    ) {
        return budgetDao.updateSpentTotal(userId, spent, month)
    }

}
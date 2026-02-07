package com.cbmoney.domain.usecase.budget

import com.cbmoney.domain.model.Budget
import com.cbmoney.domain.repository.BudgetRepository

class SaveBudgetsUseCase(
    private val budgetRepository: BudgetRepository
) {
    suspend operator fun invoke(budgets: List<Budget>): Result<Boolean> {
        return budgetRepository.upsertBudgets(budgets)
    }
}
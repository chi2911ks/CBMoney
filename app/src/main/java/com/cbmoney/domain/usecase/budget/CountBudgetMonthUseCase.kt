package com.cbmoney.domain.usecase.budget

import com.cbmoney.domain.repository.BudgetRepository

class CountBudgetMonthUseCase(
    private val budgetRepository: BudgetRepository
) {
    suspend operator fun invoke(month: String): Int {
        return budgetRepository.countBudgetsByMonth(month)
    }
}
package com.cbmoney.domain.usecase.budget

import com.cbmoney.domain.repository.BudgetRepository

class GetBudgetsMonthUseCase(
    private val budgetRepository: BudgetRepository
) {
    operator fun invoke(month: String) = budgetRepository.getBudgetsByMonth(month)
}
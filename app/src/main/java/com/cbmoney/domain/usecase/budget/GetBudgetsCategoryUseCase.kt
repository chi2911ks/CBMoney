package com.cbmoney.domain.usecase.budget

import com.cbmoney.domain.repository.BudgetRepository

class GetBudgetsCategoryUseCase(
    private val budgetRepository: BudgetRepository

) {
    operator fun invoke(month: String) = budgetRepository.getBudgetsCategoryByMonth(month)
}
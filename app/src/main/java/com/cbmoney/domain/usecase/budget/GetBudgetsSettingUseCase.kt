package com.cbmoney.domain.usecase.budget

import com.cbmoney.domain.model.BudgetSettings
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.domain.repository.BudgetRepository
import com.cbmoney.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetBudgetsSettingUseCase(
    private val budgetRepository: BudgetRepository,
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(month: String, type: CategoryType): Flow<BudgetSettings> {

        val budgets = budgetRepository.getBudgetsByMonth(month)
        val categories = categoryRepository.getCategoriesByType(type)

        return combine(budgets, categories){
            budgets, categories ->
            BudgetSettings(
                budgets = budgets,
                categories = categories
            )
        }

    }
}
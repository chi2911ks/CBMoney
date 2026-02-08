package com.cbmoney.domain.usecase.transaction

import com.cbmoney.domain.model.Budget
import com.cbmoney.domain.model.Transaction
import com.cbmoney.domain.repository.BudgetRepository
import com.cbmoney.domain.repository.TransactionRepository
import com.cbmoney.utils.exts.toFormatDate

class SaveTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val budgetRepository: BudgetRepository
) {
    suspend operator fun invoke(transaction: Transaction): Result<Boolean> {

        val result = transactionRepository.upsertTransaction(transaction)
        if (result.isFailure) return result
        

        val month = transaction.date.toFormatDate("MM/yyyy")

        if (transaction.categoryId == null) return Result.failure(Exception("Category ID is null"))

        ensureBudgetsExist(month, transaction.categoryId)
        return budgetRepository.updateSpent(transaction.categoryId, month, transaction.amount)


    }
    private suspend fun ensureBudgetsExist(month: String, categoryId: String){
        val budgets = mutableListOf<Budget>()

        budgetRepository.checkBudgetByCategoryExists(month, categoryId).onSuccess {
            if (!it) {
                budgets.add(
                    Budget(
                        categoryId = categoryId,
                        userId = "",
                        period = "monthly",
                        month = month
                    )
                )
            }
        }
        budgetRepository.checkBudgetTotalExists(month).onSuccess {
            if (!it) {
                budgets.add(
                    Budget(
                        categoryId = null,
                        userId = "",
                        period = "monthly",
                        month = month
                    )
                )
            }
        }
        if (budgets.isNotEmpty()) {
            budgetRepository.upsertBudgets(budgets)
        }
    }
}
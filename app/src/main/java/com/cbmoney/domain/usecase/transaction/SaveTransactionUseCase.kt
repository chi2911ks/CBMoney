package com.cbmoney.domain.usecase.transaction

import android.os.Build
import androidx.annotation.RequiresApi
import com.cbmoney.domain.model.Budget
import com.cbmoney.domain.model.Transaction
import com.cbmoney.domain.repository.BudgetRepository
import com.cbmoney.domain.repository.TransactionRepository
import com.cbmoney.utils.exts.toFormatDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val budgetRepository: BudgetRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(transaction: Transaction): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            val result = transactionRepository.upsertTransaction(transaction)
            if (result.isSuccess) {

                val month = transaction.date.toFormatDate("MM/yyyy")


                transaction.categoryId?.let { categoryId ->
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
                    budgetRepository.updateSpent(categoryId, month, transaction.amount)
                }

            }
            result
        }
    }
}
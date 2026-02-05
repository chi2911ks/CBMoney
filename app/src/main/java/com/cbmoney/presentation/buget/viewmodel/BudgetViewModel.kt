package com.cbmoney.presentation.buget.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.usecase.budget.GetBudgetsCategoryUseCase
import com.cbmoney.presentation.buget.contract.BudgetEvent
import com.cbmoney.presentation.buget.contract.BudgetIntent
import com.cbmoney.presentation.buget.contract.BudgetState
import com.cbmoney.utils.getYearMonthFormat
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class BudgetViewModel(
    private val getBudgetsCategoryUseCase: GetBudgetsCategoryUseCase
) : BaseMviViewModel<BudgetState, BudgetEvent, BudgetIntent>() {


    override fun initialState(): BudgetState = BudgetState()

    override fun processIntent(intent: BudgetIntent) {
        when (intent) {
            is BudgetIntent.OnChangeCurrentMonth -> {
                updateState {
                    copy(currentMonth = intent.newYearMonth)

                }
                load()
            }

            is BudgetIntent.LoadBudgets -> load()
        }
    }

    init {
        load()
    }

    private fun load() {
        val monthFormat = getYearMonthFormat(currentState.currentMonth, false)
        viewModelScope.launch {
            getBudgetsCategoryUseCase(monthFormat).collect { listBudgets ->
                val (overallBudget, budgetCategory) =
                    listBudgets.partition { it.budget.categoryId == null }
                updateState {
                    copy(
                        totalBudget = overallBudget.firstOrNull()?.budget,
                        budgetsByCategory = budgetCategory
                    )
                }
            }
        }
    }
}
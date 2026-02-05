package com.cbmoney.presentation.buget.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.model.Budget
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.domain.usecase.budget.GetBudgetsMonthUseCase
import com.cbmoney.domain.usecase.budget.UpsertBudgetsUseCase
import com.cbmoney.domain.usecase.category.GetCategoryByTypeUseCase
import com.cbmoney.presentation.buget.contract.BudgetSettingsEvent
import com.cbmoney.presentation.buget.contract.BudgetSettingsIntent
import com.cbmoney.presentation.buget.contract.BudgetSettingsState
import com.cbmoney.utils.getYearMonthFormat
import kotlinx.coroutines.launch
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
class BudgetSettingsViewModel(
    private val upsertBudgetsUseCase: UpsertBudgetsUseCase,
    private val getCategoryByTypeUseCase: GetCategoryByTypeUseCase,
    private val getBudgetsMonthUseCase: GetBudgetsMonthUseCase
) : BaseMviViewModel<BudgetSettingsState, BudgetSettingsEvent, BudgetSettingsIntent>() {
    private var budgetTotalId: String = ""
    override fun initialState(): BudgetSettingsState = BudgetSettingsState()
    override fun processIntent(intent: BudgetSettingsIntent) {
        when (intent) {
            is BudgetSettingsIntent.OnChangeBudget -> updateState {
                copy(
                    budgetsAmount = budgetsAmount.toMutableMap().apply {
                        val budgetId = get(intent.categoryId)?.get("budgetId")?:""
                        put(intent.categoryId, mapOf(
                            "budgetId" to budgetId,
                            "amount" to intent.budget
                        ))
                    }
                )

            }

            is BudgetSettingsIntent.OnChangeTotalBudget -> updateState {
                copy(totalBudget = intent.totalBudget)
            }

            is BudgetSettingsIntent.OnSaveBudget -> saveBudgets()
            is BudgetSettingsIntent.OnChangeCurrentMonth -> {
                updateState {
                    copy(currentMonth = intent.yearMonth)
                }
                load(currentState.currentMonth)
            }

            is BudgetSettingsIntent.LoadBudget -> load(currentState.currentMonth)
        }
    }

    init {
        load(currentState.currentMonth)
    }

    private fun saveBudgets() {
        viewModelScope.launch {
            val saveBudget: MutableList<Budget> = mutableListOf()
            val budgetMonth = getYearMonthFormat(currentState.currentMonth, isStartAndEndDate = false)
            saveBudget.add(
                Budget(
                    id = budgetTotalId,
                    amount = currentState.totalBudget,
                    categoryId = null,
                    userId = "",
                    month = budgetMonth,
                    period = "monthly",
                )
            )
            val filtered = currentState.budgetsAmount.filter { it.value["amount"] as Long > 0L }

            for (item in filtered) {
                saveBudget.add(
                    Budget(
                        id = item.value["budgetId"] as String,
                        amount = item.value["amount"] as Long,
                        categoryId = item.key,
                        userId = "",
                        month = budgetMonth,
                        period = "monthly",
                    )
                )
            }
            upsertBudgetsUseCase(saveBudget).onSuccess {
                sendEvent(BudgetSettingsEvent.Close)
            }.onFailure {
                sendEvent(BudgetSettingsEvent.SaveError(it.message ?: ""))
            }
        }
    }

    private fun load(yearMonth: YearMonth) {
        val month = getYearMonthFormat(yearMonth, isStartAndEndDate = false)
        viewModelScope.launch {
            getCategoryByTypeUseCase(CategoryType.EXPENSE.name.lowercase()).collect { categories ->
                updateState {
                    copy(
                        categories = categories,
                    )
                }
            }
        }
        viewModelScope.launch {
            getBudgetsMonthUseCase(month).collect { budgets ->
                val (filtered, totalBudget) =
                    budgets.partition { it.categoryId != null }

                budgetTotalId = totalBudget.firstOrNull()?.id ?: ""
                updateState {
                    copy(
                        totalBudget = totalBudget.firstOrNull()?.amount ?: 0,
                        budgetsAmount = filtered.associate { budget ->
                            budget.categoryId!! to mapOf(
                                "budgetId" to budget.id,
                                "amount" to budget.amount
                            )
                        }
                    )
                }

            }
        }
    }
}
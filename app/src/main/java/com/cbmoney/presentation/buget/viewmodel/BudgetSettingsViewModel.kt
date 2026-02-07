package com.cbmoney.presentation.buget.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.model.Budget
import com.cbmoney.domain.model.BudgetCategory
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.domain.usecase.budget.GetBudgetsSettingUseCase
import com.cbmoney.domain.usecase.budget.SaveBudgetsUseCase
import com.cbmoney.presentation.buget.contract.BudgetSettingsEvent
import com.cbmoney.presentation.buget.contract.BudgetSettingsIntent
import com.cbmoney.presentation.buget.contract.BudgetSettingsState
import com.cbmoney.utils.getYearMonthFormat
import kotlinx.coroutines.launch
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
class BudgetSettingsViewModel(
    private val saveBudgetsUseCase: SaveBudgetsUseCase,
    private val getBudgetsSettingUseCase: GetBudgetsSettingUseCase
) : BaseMviViewModel<BudgetSettingsState, BudgetSettingsEvent, BudgetSettingsIntent>() {
    override fun initialState(): BudgetSettingsState = BudgetSettingsState()
    override fun processIntent(intent: BudgetSettingsIntent) {
        when (intent) {
            is BudgetSettingsIntent.OnChangeBudget -> updateState {
                copy(
                    budgetsCategory = budgetsCategory.toMutableMap().apply {
                        var budget = get(intent.categoryId)
                        budget?.let {
                            budget = it.copy(
                                budget = it.budget.copy(
                                    amount = intent.budget
                                )
                            )
                            put(intent.categoryId, budget)
                        }
                    }
                )

            }

            is BudgetSettingsIntent.OnChangeTotalBudget -> updateState {
                copy(totalBudget = totalBudget?.copy(amount = intent.totalBudget))
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
            currentState.totalBudget?.let {
                saveBudget.add(it)
            }

            saveBudget.addAll(currentState.budgetsCategory.values.map { it.budget }.filter { it.amount > 0 })
            saveBudgetsUseCase(saveBudget).onSuccess {
                sendEvent(BudgetSettingsEvent.Close)
            }.onFailure {
                sendEvent(BudgetSettingsEvent.SaveError(it.message ?: ""))
            }
        }
    }

    private fun load(yearMonth: YearMonth) {
        val month = getYearMonthFormat(yearMonth, isStartAndEndDate = false)
        viewModelScope.launch {
            getBudgetsSettingUseCase(month,CategoryType.EXPENSE).collect {budgetSettings ->
                val (totalBudget, budgets) = budgetSettings.budgets.partition {
                    it.categoryId == null
                }
                val sv = mutableMapOf<String, BudgetCategory>()
                budgetSettings.categories.forEach {
                    sv[it.id] = BudgetCategory(
                        budget = budgets.findLast { budget -> budget.categoryId == it.id } ?: Budget(
                            categoryId = it.id,
                            userId = "",
                            period = "monthly",
                            month = month
                        ),
                        categoryName = it.name,
                        categoryIcon = it.icon,
                        iconColor = it.iconColor,
                    )
                }
                updateState {
                    copy(
                        totalBudget = totalBudget.firstOrNull()?:Budget(
                            categoryId = null,
                            userId = "",
                            period = "monthly",
                            month = month
                        ),
                        budgetsCategory = sv
                    )
                }
            }
        }
    }
}
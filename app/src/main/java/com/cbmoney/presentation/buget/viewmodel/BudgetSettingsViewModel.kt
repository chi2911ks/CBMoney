package com.cbmoney.presentation.buget.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.model.Budget
import com.cbmoney.domain.model.BudgetDetails
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.domain.usecase.budget.GetBudgetsSettingUseCase
import com.cbmoney.domain.usecase.budget.SaveBudgetsUseCase
import com.cbmoney.presentation.buget.contract.BudgetSettingsEvent
import com.cbmoney.presentation.buget.contract.BudgetSettingsIntent
import com.cbmoney.presentation.buget.contract.BudgetSettingsState
import com.cbmoney.utils.DateUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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
            }


        }
    }

    init {
        load()
    }

    private fun saveBudgets() {
        viewModelScope.launch {
            val saveBudget = currentState.budgetsCategory.values.map { it.budget }.filter { it.amount > 0 }.toMutableList()
            currentState.totalBudget?.let {
                 saveBudget.add(it)
            }

            saveBudgetsUseCase(saveBudget).onSuccess {
                sendEvent(BudgetSettingsEvent.Close)
            }.onFailure {
                sendEvent(BudgetSettingsEvent.SaveError(it.message ?: ""))
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun load() {
        viewState.map{
            it.currentMonth
        }.distinctUntilChanged().map {
            DateUtils.getYearMonthFormat(it, isStartAndEndDate = false)
        }.flatMapLatest {
            getBudgetsSettingUseCase(it,CategoryType.EXPENSE)
        }.onEach {budgetSettings->
            val (totalBudget, budgets) = budgetSettings.budgets.partition {
                it.isOverall
            }
            val monthFormat = DateUtils.getYearMonthFormat(currentState.currentMonth, isStartAndEndDate = false)


            updateState {
                copy(
                    totalBudget = totalBudget.firstOrNull()?:Budget(
                        categoryId = null,
                        userId = "",
                        period = "monthly",
                        month = monthFormat
                    ),
                    budgetsCategory = budgetSettings.categories.associate {
                        it.id to BudgetDetails(
                            budget = budgets.findLast { budget -> budget.categoryId == it.id } ?: Budget(
                                categoryId = it.id,
                                userId = "",
                                period = "monthly",
                                month = monthFormat
                            ),
                            categoryName = it.name,
                            categoryIcon = it.icon,
                            iconColor = it.iconColor,
                        )
                    }
                )
            }
        }.launchIn(viewModelScope)


    }
}
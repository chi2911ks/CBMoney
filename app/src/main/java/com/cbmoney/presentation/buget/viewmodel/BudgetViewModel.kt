package com.cbmoney.presentation.buget.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.usecase.budget.GetBudgetsCategoryUseCase
import com.cbmoney.presentation.buget.contract.BudgetEvent
import com.cbmoney.presentation.buget.contract.BudgetIntent
import com.cbmoney.presentation.buget.contract.BudgetState
import com.cbmoney.utils.DateUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

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
            }
        }
    }

    init {
        load()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun load() {
        //distinctUntilChanged dùng để tránh lặp lại nhiều lần
        //flatMapLatest dùng để cancel flow cũ khi chạy flow mới
        viewState.map {
            it.currentMonth
        }.distinctUntilChanged().map {
            DateUtils.getYearMonthFormat(it, false)
        }.flatMapLatest { monthFormat ->
            getBudgetsCategoryUseCase(monthFormat)
        }.onEach { listBudgets ->
            val (overallBudget, budgetCategory) =
                listBudgets.partition { it.budget.isOverall }
            updateState {
                copy(
                    totalBudget = overallBudget.firstOrNull()?.budget,
                    budgetsByCategory = budgetCategory.filter { it.budget.amount > 0 }
                )
            }
        }.launchIn(viewModelScope)
    }
}
package com.cbmoney.presentation.reports

import androidx.collection.intSetOf
import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.usecase.transaction.GetCategorySpendingUseCase
import com.cbmoney.domain.usecase.transaction.GetTotalSummaryUseCase
import com.cbmoney.utils.DateUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.core.KoinApplication.Companion.init

class ReportViewModel(
    private val getCategorySpendingUseCase: GetCategorySpendingUseCase,
    private val getTotalSummaryUseCase: GetTotalSummaryUseCase
): BaseMviViewModel<ReportState, ReportEvent, ReportIntent>() {
    override fun initialState(): ReportState = ReportState()

    override fun processIntent(intent: ReportIntent) {
        when(intent){
            is ReportIntent.OnChangeCurrentMonth-> {
                updateState {
                    copy(yearMonth = intent.newYearMonth)
                }
            }
            is ReportIntent.OnChangeCategoryType-> {
                updateState {
                    copy(categoryType = intent.newCategoryType)
                }
            }
        }
    }
    init {
        observeSpendingData()
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSpendingData(){
        viewState.map {
            it.yearMonth
        }.distinctUntilChanged().map {
            DateUtils.getMonthRange(it.year, it.monthValue)
        }.flatMapLatest {range->
            combine(
                getCategorySpendingUseCase(range.first, range.second),
                getTotalSummaryUseCase(range.first, range.second)
            ) { spending, total ->
                spending to total
            }
        }.onEach {
            updateState {
                copy(
                    spendingCategories = it.first,
                    totalSummary = it.second
                )
            }
        }.launchIn(viewModelScope)


    }

}
package com.cbmoney.presentation.reports

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.domain.usecase.transaction.GetCategorySpendingUseCase
import com.cbmoney.domain.usecase.transaction.GetTotalSummaryUseCase
import com.cbmoney.utils.DateUtils
import kotlinx.coroutines.launch

class ReportViewModel(
    private val getCategorySpendingUseCase: GetCategorySpendingUseCase,
    private val getTotalSummaryUseCase: GetTotalSummaryUseCase
): BaseMviViewModel<ReportState, ReportEvent, ReportIntent>() {
    override fun initialState(): ReportState = ReportState()

    override fun processIntent(intent: ReportIntent) {

    }
    init {
        load()
    }
    private fun load(){
        val (start, end) = DateUtils.getMonthRange(2026, 2)
        viewModelScope.launch {
            getCategorySpendingUseCase(start, end).collect {
                updateState {
                    copy(
                        spendingCategories = it,
                    )

                }
            }
        }
        viewModelScope.launch {
            getTotalSummaryUseCase(start, end).collect {
                updateState {
                    copy(
                        totalSummary = it
                    )
                }
            }
        }

    }

}
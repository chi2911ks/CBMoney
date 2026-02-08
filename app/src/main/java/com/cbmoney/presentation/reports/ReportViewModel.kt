package com.cbmoney.presentation.reports

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.data.local.room.dao.CategoryDao
import com.cbmoney.utils.DateUtils
import kotlinx.coroutines.launch

class ReportViewModel(
    private val categoryDao: CategoryDao,
): BaseMviViewModel<ReportState, ReportEvent, ReportIntent>() {
    override fun initialState(): ReportState = ReportState()

    override fun processIntent(intent: ReportIntent) {

    }
    init {
        load()
    }
    private fun load(){
        viewModelScope.launch {
            val (start, end) = DateUtils.getMonthRange(2026, 2)
            categoryDao.getCategoriesSpending("ccR6LVrG1xWj4QOOLPAPDFcNDUh1", start, end).collect{
                updateState {
                    copy(
                        categories = it

                    )
                }
            }
        }

    }

}
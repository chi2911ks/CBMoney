package com.cbmoney.presentation.reports

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.domain.model.CategorySpending
import com.cbmoney.domain.model.FinancialSummary

data class ReportState(
    val totalSummary: FinancialSummary = FinancialSummary(),
    val spendingCategories: List<CategorySpending> = emptyList()
): MviState

sealed class ReportEvent: MviEvent{

}
sealed class ReportIntent: MviIntent {

}
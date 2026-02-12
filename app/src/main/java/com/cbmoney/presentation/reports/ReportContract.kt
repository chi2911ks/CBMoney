package com.cbmoney.presentation.reports

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.domain.model.CategorySpending
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.domain.model.FinancialSummary
import java.time.YearMonth

data class ReportState(
    val yearMonth: YearMonth = YearMonth.now(),
    val categoryType: CategoryType = CategoryType.EXPENSE,
    val totalSummary: FinancialSummary = FinancialSummary(),
    val spendingCategories: List<CategorySpending> = emptyList()
): MviState

sealed class ReportEvent: MviEvent{

}
sealed class ReportIntent: MviIntent {
    data class OnChangeCurrentMonth(val newYearMonth: YearMonth): ReportIntent()
    data class OnChangeCategoryType(val newCategoryType: CategoryType): ReportIntent()

}
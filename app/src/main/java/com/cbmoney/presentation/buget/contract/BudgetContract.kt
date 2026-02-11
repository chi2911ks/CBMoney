package com.cbmoney.presentation.buget.contract

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.domain.model.Budget
import com.cbmoney.domain.model.BudgetDetails
import java.time.YearMonth
data class BudgetState(
    val isLoading: Boolean = false,
    val currentMonth: YearMonth = YearMonth.now(),
    val totalBudget: Budget? = null,
    val budgetsByCategory: List<BudgetDetails> = emptyList()
): MviState
sealed class BudgetEvent: MviEvent {

}
sealed class BudgetIntent: MviIntent {
    data class OnChangeCurrentMonth(val newYearMonth: YearMonth): BudgetIntent()
}


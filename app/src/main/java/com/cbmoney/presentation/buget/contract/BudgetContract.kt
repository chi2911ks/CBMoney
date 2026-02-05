package com.cbmoney.presentation.buget.contract

import android.os.Build
import androidx.annotation.RequiresApi
import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.domain.model.Budget
import com.cbmoney.domain.model.BudgetCategory
import java.time.YearMonth
@RequiresApi(Build.VERSION_CODES.O)
data class BudgetState(
    val isLoading: Boolean = false,
    val currentMonth: YearMonth = YearMonth.now(),
    val totalBudget: Budget? = null,
    val budgetsByCategory: List<BudgetCategory> = emptyList()
): MviState
sealed class BudgetEvent: MviEvent {

}
sealed class BudgetIntent: MviIntent {
    object LoadBudgets: BudgetIntent()
    data class OnChangeCurrentMonth(val newYearMonth: YearMonth): BudgetIntent()
}


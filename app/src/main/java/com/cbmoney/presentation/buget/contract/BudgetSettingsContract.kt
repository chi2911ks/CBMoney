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
data class BudgetSettingsState(

    val currentMonth: YearMonth = YearMonth.now(),
//    val budgetsAmount: Map<String, Map<String, Any>> = emptyMap(),
//    val categories: List<Category> = emptyList(),

    val totalBudget: Budget? = null,
    val budgetsCategory: Map<String, BudgetCategory> = emptyMap(),
) : MviState

//sealed class State: MviState{
//    data class Error(
//        val message: String,
//        val title: String,
//    ): State()
//}


sealed class BudgetSettingsEvent: MviEvent {
    data object Close: BudgetSettingsEvent()
    data class SaveError(val message: String): BudgetSettingsEvent()
}
sealed class BudgetSettingsIntent: MviIntent {
    data class OnChangeBudget(val categoryId: String, val budget: Long): BudgetSettingsIntent()
    data class OnChangeTotalBudget(val totalBudget: Long): BudgetSettingsIntent()
    data class OnChangeCurrentMonth(val yearMonth: YearMonth): BudgetSettingsIntent()
    data object LoadBudget: BudgetSettingsIntent()

    data object OnSaveBudget: BudgetSettingsIntent()

}
package com.cbmoney.presentation.buget.contract

import com.cbmoney.base.MviState

data class BudgetSettingsState(
    val totalBudget: Long = 0L,
    val budgets: Map<String, Long> = emptyMap()
) : MviState
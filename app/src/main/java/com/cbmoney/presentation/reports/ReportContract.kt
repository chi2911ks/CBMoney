package com.cbmoney.presentation.reports

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.data.local.room.entities.CategorySpendingEntity

data class ReportState(
    val isLoading: Boolean = false,
    val categories: List<CategorySpendingEntity> = emptyList()
): MviState

sealed class ReportEvent: MviEvent{

}
sealed class ReportIntent: MviIntent {

}
package com.cbmoney.presentation.home

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState

data class HomeState(
    val name: String = ""
): MviState

sealed class HomeIntent(): MviIntent{
    object OnClickProfile: HomeIntent()
    object OnClickNotification: HomeIntent()
}
sealed class HomeEvent(): MviEvent {

}
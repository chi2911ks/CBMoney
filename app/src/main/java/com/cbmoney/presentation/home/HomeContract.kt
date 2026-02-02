package com.cbmoney.presentation.home

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.domain.model.User

data class HomeState(
    val user: User? = null,
): MviState

sealed class HomeIntent(): MviIntent{
    object OnClickProfile: HomeIntent()
    object OnClickNotification: HomeIntent()
}
sealed class HomeEvent(): MviEvent {

}
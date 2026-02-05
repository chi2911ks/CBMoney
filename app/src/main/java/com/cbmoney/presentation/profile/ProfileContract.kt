package com.cbmoney.presentation.profile

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.domain.model.User

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
) : MviState

sealed class ProfileEvent : MviEvent {
    data object LogOut: ProfileEvent()
}

sealed class ProfileIntent : MviIntent {
    data object LoadProfile : ProfileIntent()
    data object LogOut : ProfileIntent()
}
package com.cbmoney.presentation.profile

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState

data class ProfileState(
    val imageURL: String? = null,
    val name: String = "",
    val email: String = "",
    val isLoading: Boolean = false
) : MviState

sealed class ProfileEvent : MviEvent {
    data object LogOut: ProfileEvent()
}

sealed class ProfileIntent : MviIntent {
    data object LoadProfile : ProfileIntent()
    data object LogOut : ProfileIntent()
}
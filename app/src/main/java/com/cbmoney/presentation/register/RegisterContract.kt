package com.cbmoney.presentation.register

import android.app.Activity
import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.domain.auth.model.AuthError

data class RegisterState(
    val isLoading: Boolean = false,
) : MviState

sealed class RegisterEvent() : MviEvent {
    data object NavigateToHome : RegisterEvent()
    data class RegisterError(val authError: AuthError) : RegisterEvent()
}

sealed class RegisterIntent() : MviIntent {
    data class RegisterEmail(val username: String, val password: String) : RegisterIntent()
    data class GoogleRegister(val activity: Activity) : RegisterIntent()
}

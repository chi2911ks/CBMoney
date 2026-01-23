package com.cbmoney.presentation.login

import android.app.Activity
import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.domain.auth.model.AuthError

data class LoginState(
    val isLoading: Boolean = false,
): MviState

sealed class LoginEvent: MviEvent {
    data object NavigateToHome: LoginEvent()
    data class LoginError(val authError: AuthError): LoginEvent()
}
sealed class LoginIntent: MviIntent {
    data class GoogleLogin(val activity: Activity): LoginIntent()
    data class EmailLogin(val username: String, val password: String): LoginIntent()
}
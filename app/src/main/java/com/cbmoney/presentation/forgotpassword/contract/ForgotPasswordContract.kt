package com.cbmoney.presentation.forgotpassword.contract

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.data.provider.model.AuthError

data class ForgotPasswordState(
    val isLoading: Boolean = false
) : MviState

sealed class ForgotPasswordEvent : MviEvent {
    data class ResetPasswordError(val authError: AuthError) : ForgotPasswordEvent()
    data object ResetPasswordSuccess : ForgotPasswordEvent()
}

sealed class ForgotPasswordIntent : MviIntent {
    data class SendResetEmail(val email: String) : ForgotPasswordIntent()
}

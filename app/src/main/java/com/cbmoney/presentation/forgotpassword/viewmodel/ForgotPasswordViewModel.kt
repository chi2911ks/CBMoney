package com.cbmoney.presentation.forgotpassword.viewmodel

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.data.provider.EmailAuthClient
import com.cbmoney.data.provider.model.AuthError
import com.cbmoney.data.provider.model.AuthResult
import com.cbmoney.presentation.forgotpassword.contract.ForgotPasswordEvent
import com.cbmoney.presentation.forgotpassword.contract.ForgotPasswordIntent
import com.cbmoney.presentation.forgotpassword.contract.ForgotPasswordState
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val emailAuthClient: EmailAuthClient
) : BaseMviViewModel<ForgotPasswordState, ForgotPasswordEvent, ForgotPasswordIntent>() {

    override fun initialState(): ForgotPasswordState = ForgotPasswordState()

    override fun processIntent(intent: ForgotPasswordIntent) {
        when (intent) {
            is ForgotPasswordIntent.SendResetEmail -> handleSendResetEmail(intent.email)
        }
    }

    private fun handleSendResetEmail(email: String) {
        if (email.isBlank()) {
            sendEvent(ForgotPasswordEvent.ResetPasswordError(AuthError.EmptyField))
            return
        }
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = emailAuthClient.resetPassword(email)
            when (result) {
                is AuthResult.Success -> {
                    sendEvent(ForgotPasswordEvent.ResetPasswordSuccess)
                }
                is AuthResult.Failure -> {
                    sendEvent(ForgotPasswordEvent.ResetPasswordError(result.error))
                }
            }
            updateState { copy(isLoading = false) }
        }
    }
}

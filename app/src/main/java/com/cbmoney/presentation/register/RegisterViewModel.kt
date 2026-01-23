package com.cbmoney.presentation.register

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.data.provider.EmailAuth
import com.cbmoney.data.provider.GoogleAuth
import com.cbmoney.domain.auth.model.AuthResult
import kotlinx.coroutines.launch


class RegisterViewModel(
    private val googleAuth: GoogleAuth,
    private val emailAuth: EmailAuth
) : BaseMviViewModel<RegisterState, RegisterEvent, RegisterIntent>() {
    override fun initialState(): RegisterState = RegisterState()
    override fun processIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.RegisterEmail -> handleRegisterEmail(intent.username, intent.password)
            is RegisterIntent.GoogleRegister -> handleGoogleRegister(intent.activity)
        }
    }

    private fun handleRegisterEmail(username: String, password: String) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = emailAuth.createAccount(username, password)
            when (result) {
                is AuthResult.Success -> {
                    sendEvent(RegisterEvent.NavigateToHome)
                }

                is AuthResult.Failure -> {
                    sendEvent(RegisterEvent.RegisterError(result.error))
                }

            }
            updateState { copy(isLoading = false) }
        }
    }

    private fun handleGoogleRegister(activity: Activity) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = googleAuth.signInGoogle(activity)
            if (result) {
                sendEvent(RegisterEvent.NavigateToHome)
            } else {
//                sendEvent(RegisterEvent.RegisterError())
            }
            updateState { copy(isLoading = false) }
        }

    }
}
package com.cbmoney.presentation.login

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.data.provider.EmailAuth
import com.cbmoney.data.provider.GoogleAuth
import com.cbmoney.domain.auth.model.AuthError
import com.cbmoney.domain.auth.model.AuthResult
import kotlinx.coroutines.launch


class LoginViewModel(
    private val googleAuth: GoogleAuth,
    private val emailAuth: EmailAuth
): BaseMviViewModel<LoginState, LoginEvent, LoginIntent>() {
    override fun initialState(): LoginState = LoginState()


    override fun processIntent(intent: LoginIntent) {
        when(intent){
            is LoginIntent.GoogleLogin -> handleGoogleAuth(intent.activity)
            is LoginIntent.EmailLogin -> handleEmailAuth(intent.username, intent.password)
        }
    }
    private fun handleGoogleAuth(activity: Activity){
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = googleAuth.signInGoogle(activity)
            if (result){
                sendEvent(LoginEvent.NavigateToHome)
            }
            else{
                sendEvent(LoginEvent.LoginError(AuthError.Fail))
            }
            updateState { copy(isLoading = false) }
        }
    }
    private fun handleEmailAuth(username: String, password: String){
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = emailAuth.signIn(username, password)
            when(result){
                is AuthResult.Success -> {
                    sendEvent(LoginEvent.NavigateToHome)
                }
                is AuthResult.Failure -> {
                    sendEvent(LoginEvent.LoginError(result.error))
                }
            }

            updateState { copy(isLoading = false) }
        }
    }
}
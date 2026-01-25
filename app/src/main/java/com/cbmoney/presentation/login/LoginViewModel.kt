package com.cbmoney.presentation.login

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.data.provider.EmailAuth
import com.cbmoney.data.provider.GoogleAuth
import com.cbmoney.data.provider.model.AuthError
import com.cbmoney.data.provider.model.AuthResult
import com.cbmoney.domain.model.User
import com.cbmoney.domain.usecase.user.GetUserUseCase
import com.cbmoney.domain.usecase.user.SaveUserToUseCase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


class LoginViewModel(
    private val googleAuth: GoogleAuth,
    private val emailAuth: EmailAuth,
    private val firebaseAuth: FirebaseAuth,
    private val getUserUseCase: GetUserUseCase,
    private val saveUserToUseCase: SaveUserToUseCase
) : BaseMviViewModel<LoginState, LoginEvent, LoginIntent>() {
    override fun initialState(): LoginState = LoginState()


    override fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.GoogleLogin -> handleGoogleAuth(intent.activity)
            is LoginIntent.EmailLogin -> handleEmailAuth(intent.username, intent.password)
        }
    }

    private fun handleGoogleAuth(activity: Activity) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = googleAuth.signInGoogle(activity)
            if (result) {
                checkUser()

            } else {
                sendEvent(LoginEvent.LoginError(AuthError.Fail))
            }
            updateState { copy(isLoading = false) }
        }
    }

    private fun handleEmailAuth(username: String, password: String) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = emailAuth.signIn(username, password)
            when (result) {
                is AuthResult.Success -> checkUser()
                is AuthResult.Failure -> {
                    sendEvent(LoginEvent.LoginError(result.error))
                }
            }
            updateState { copy(isLoading = false) }
        }
    }

    private suspend fun checkUser() {

        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            googleAuth.signOut()
            sendEvent(LoginEvent.LoginError(AuthError.Fail))
            return
        }
        val getUser = getUserUseCase(currentUser.uid)
        getUser.onSuccess { user ->
            if (user == null) {
                val saveUser = User(
                    id = currentUser.uid,
                    name = currentUser.displayName.orEmpty(),
                    email = currentUser.email.orEmpty(),
                    photoUrl = currentUser.photoUrl.toString(),
                    createdAt = Timestamp.now().toDate().toString()
                )
                saveUserToUseCase(saveUser)
            } else {
                saveUserToUseCase(user)
            }
            sendEvent(LoginEvent.NavigateToHome)
        }
        getUser.onFailure {
            googleAuth.signOut()
            sendEvent(LoginEvent.LoginError(AuthError.Fail))
        }
    }
}
package com.cbmoney.presentation.login

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.cbmoney.data.provider.EmailAuthClient
import com.cbmoney.data.provider.GoogleAuthClient
import com.cbmoney.data.provider.model.AuthError
import com.cbmoney.data.provider.model.AuthResult
import com.cbmoney.domain.model.User
import com.cbmoney.domain.usecase.category.InitCategoriesDefaultUseCase
import com.cbmoney.domain.usecase.user.GetUserUseCase
import com.cbmoney.domain.usecase.user.SaveUserToUseCase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


class LoginViewModel(
    private val googleAuthClient: GoogleAuthClient,
    private val emailAuthClient: EmailAuthClient,
    private val firebaseAuth: FirebaseAuth,
    private val getUserUseCase: GetUserUseCase,
    private val saveUserToUseCase: SaveUserToUseCase,
    private val initCategoriesDefaultUseCase: InitCategoriesDefaultUseCase
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
            googleAuthClient.signInGoogle(activity).fold(
                onSuccess = {
                    checkUser(it)
                },
                onFailure = {
                    googleAuthClient.signOut()
                    sendEvent(LoginEvent.LoginError(AuthError.Fail))
                }
            )
            updateState { copy(isLoading = false) }
        }
    }

    private fun handleEmailAuth(username: String, password: String) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val result = emailAuthClient.signIn(username, password)
            when (result) {
                is AuthResult.Success -> checkUser(result.user)
                is AuthResult.Failure -> {
                    sendEvent(LoginEvent.LoginError(result.error))
                }
            }
            updateState { copy(isLoading = false) }
        }
    }

    private suspend fun checkUser(currentUser: FirebaseUser?) {

        if (currentUser == null) {
            googleAuthClient.signOut()
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
                    createdAt = Timestamp.now().toDate().time
                )
                saveUserToUseCase(saveUser)
            } else {
                saveUserToUseCase(user)
            }
            initCategoriesDefaultUseCase()
            sendEvent(LoginEvent.NavigateToHome)
        }
        getUser.onFailure {
            googleAuthClient.signOut()
            sendEvent(LoginEvent.LoginError(AuthError.Fail))
        }
    }
}
package com.cbmoney.presentation.register

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


class RegisterViewModel(
    private val googleAuthClient: GoogleAuthClient,
    private val emailAuthClient: EmailAuthClient,
    private val firebaseAuth: FirebaseAuth,
    private val getUserUseCase: GetUserUseCase,
    private val saveUserToUseCase: SaveUserToUseCase,
    private val initCategoriesDefaultUseCase: InitCategoriesDefaultUseCase
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
            val result = emailAuthClient.createAccount(username, password)
            when (result) {
                is AuthResult.Success -> {
                    checkUser(result.user)
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
            googleAuthClient.signInGoogle(activity).fold(
                onSuccess = {
                    checkUser(it)
                },
                onFailure = {
                    googleAuthClient.signOut()
                    sendEvent(RegisterEvent.RegisterError(AuthError.Fail))
                }
            )
            updateState { copy(isLoading = false) }
        }

    }
    private suspend fun checkUser(currentUser: FirebaseUser?) {

        if (currentUser == null) {
            googleAuthClient.signOut()
            sendEvent(RegisterEvent.RegisterError(AuthError.Fail))
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
            sendEvent(RegisterEvent.NavigateToHome)
        }
        getUser.onFailure {
            googleAuthClient.signOut()
            sendEvent(RegisterEvent.RegisterError(AuthError.Fail))
        }
    }
}
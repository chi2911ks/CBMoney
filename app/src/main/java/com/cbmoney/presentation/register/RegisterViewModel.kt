package com.cbmoney.presentation.register

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


class RegisterViewModel(
    private val googleAuth: GoogleAuth,
    private val emailAuth: EmailAuth,
    private val firebaseAuth: FirebaseAuth,
    private val getUserUseCase: GetUserUseCase,
    private val saveUserToUseCase: SaveUserToUseCase,
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
                val currentUser = firebaseAuth.currentUser
                if (currentUser == null){
                    googleAuth.signOut()
                    sendEvent(RegisterEvent.RegisterError(AuthError.Fail))
                    return@launch
                }
                val getUser = getUserUseCase(currentUser.uid)
                getUser.onSuccess { user->
                    if (user == null){
                        val saveUser = User(
                            id = currentUser.uid,
                            name = currentUser.displayName.orEmpty(),
                            email = currentUser.email.orEmpty(),
                            photoUrl = currentUser.photoUrl.toString(),
                            createdAt = Timestamp.now().toDate().toString()
                        )
                        saveUserToUseCase(saveUser)
                    }else{
                        saveUserToUseCase(user)
                    }
                    sendEvent(RegisterEvent.NavigateToHome)
                }
                getUser.onFailure {
                    googleAuth.signOut()
                    sendEvent(RegisterEvent.RegisterError(AuthError.Fail))
                }
                sendEvent(RegisterEvent.NavigateToHome)
            } else {
//                sendEvent(RegisterEvent.RegisterError())
                sendEvent(RegisterEvent.RegisterError(AuthError.Fail))
            }
            updateState { copy(isLoading = false) }
        }

    }
}
package com.cbmoney.presentation.splash

import androidx.lifecycle.viewModelScope
import com.cbmoney.base.BaseMviViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


class SplashViewModel(
    private val firebaseAuth: FirebaseAuth
) : BaseMviViewModel<SplashState, SplashEvent, SplashIntent>() {
    override fun initialState() = SplashState()

    init {
        checkLogin()
    }

    override fun processIntent(intent: SplashIntent) {
        when (intent) {
            is SplashIntent.CheckLogin -> handleCheckLogin()
        }
    }

    private fun handleCheckLogin() {
        val user = firebaseAuth.currentUser
        updateState { copy(isLogged = user != null) }
//        if (user == null) {
//            updateState { copy(nextScreen = NextScreen.ON_BOARDING) }
//        } else {
//            updateState { copy(nextScreen = NextScreen.MAIN) }
//        }
    }

    private fun checkLogin() {
        viewModelScope.launch {
            viewState.collect {
//                sendEvent(
//                    when (it.nextScreen) {
//                        NextScreen.ON_BOARDING -> SplashEvent.NavigateToOnBoarding
//                        NextScreen.MAIN -> SplashEvent.NavigateToMain
//                        NextScreen.SIGN_IN -> SplashEvent.NavigateToSignIn
//                        null -> TODO()
//                    }
//                )
                sendEvent(
                    when(it.isLogged){
                        true -> SplashEvent.NavigateToMain
                        false -> SplashEvent.NavigateToSignIn
                    }
                )
            }
        }

//        val nextScreen = viewState.value.nextScreen
//        if (nextScreen == null) return
//        sendEvent(
//            when (nextScreen) {
//                NextScreen.ON_BOARDING -> SplashEvent.NavigateToOnBoarding
//                NextScreen.MAIN -> SplashEvent.NavigateToMain
//                NextScreen.SIGN_IN -> SplashEvent.NavigateToSignIn
//            }
//        )

    }
}
package com.cbmoney.presentation.splash

import com.cbmoney.base.MviEvent
import com.cbmoney.base.MviIntent
import com.cbmoney.base.MviState
import com.cbmoney.presentation.splash.model.NextScreen

data class SplashState(
    val isLogged: Boolean = false,
    val nextScreen: NextScreen? = null,
) : MviState

sealed class SplashEvent : MviEvent {
    data object NavigateToOnBoarding : SplashEvent()
    data object NavigateToMain : SplashEvent()
    data object NavigateToSignIn : SplashEvent()
}

sealed class SplashIntent : MviIntent {
    data object CheckLogin : SplashIntent()
}
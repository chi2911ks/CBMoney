package com.cbmoney.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.cbmoney.presentation.login.LoginScreen
import com.cbmoney.presentation.main.MainScreen
import com.cbmoney.presentation.onboarding.OnBoardingScreen
import com.cbmoney.presentation.register.RegisterScreen

@Composable
fun NavRoutes() {
    val backStack = rememberNavBackStack(Destination.Main).apply {
        NavDisplay(
            backStack = this,
            onBack = { removeLastOrNull() },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Destination.Onboarding> {
                    OnBoardingScreen(onContinueClicked = {
                        add(Destination.Login)
                    })
                }
                entry<Destination.Login> {
                    LoginScreen(onRegister = {
                        add(Destination.Register)
                    })

                }
                entry<Destination.Register> {
                    RegisterScreen(onBackClick = {
                        add(Destination.Main)
                    })
                }
                entry<Destination.Main> {
                    MainScreen()
                }
            },
            // Navigate forward
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(1000)
                ) + fadeIn() togetherWith
                        slideOutHorizontally(
                            targetOffsetX = { -it / 3 },
                            animationSpec = tween(1000)
                        ) + fadeOut()
            },

            // Pop back
            popTransitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { -it / 3 },
                    animationSpec = tween(1000)
                ) + fadeIn() togetherWith
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(1000)
                        ) + fadeOut()
            },

            // Predictive back (Android 14+)
            predictivePopTransitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { -it / 4 }
                ) togetherWith
                        slideOutHorizontally(
                            targetOffsetX = { it }
                        )
            }
        )
    }
}


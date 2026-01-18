package com.cbmoney.presentation.navigation

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
    val backStack = rememberNavBackStack(Destination.Main)
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Destination.Onboarding> {
                OnBoardingScreen(onContinueClicked = {
                    backStack.add(Destination.Login)
                })
            }
            entry<Destination.Login> {
                LoginScreen(onRegister = {
                    backStack.add(Destination.Register)
                })

            }
            entry<Destination.Register> {
                RegisterScreen(onBackClick = {
                    backStack.add(Destination.Main)
                })
            }
            entry<Destination.Main> {
                MainScreen()
            }
        }
    )
}


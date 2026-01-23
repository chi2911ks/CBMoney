package com.cbmoney.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.cbmoney.presentation.login.LoginScreen
import com.cbmoney.presentation.main.MainScreen
import com.cbmoney.presentation.onboarding.OnBoardingScreen
import com.cbmoney.presentation.register.RegisterScreen
import com.cbmoney.presentation.settings.LanguageBottomSheet
import com.cbmoney.presentation.settings.SettingsScreen
import com.cbmoney.presentation.splash.SplashScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavRoutes() {
    val bottomSheetStrategy = remember { BottomSheetSceneStrategy<NavKey>() }
    val backStack = rememberNavBackStack(Destination.Splash).apply {
        NavDisplay(
            backStack = this,
            onBack = { removeLastOrNull() },
            sceneStrategy = bottomSheetStrategy,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Destination.Splash> {
                    SplashScreen(
                        navigateToHome = {
                            removeLastOrNull()
                            add(Destination.Main)
                        },
                        navigateToOnBoarding = {
                            add(Destination.Onboarding)
                        },
                        navigateToSignIn = {
                            add(Destination.Login)
                        }
                    )
                }
                entry<Destination.Onboarding> {
                    OnBoardingScreen(onContinueClicked = {
                        add(Destination.Login)
                    })
                }
                entry<Destination.Login> {
                    LoginScreen(
                        navigateToHome = {
                            removeLastOrNull()
                            add(Destination.Main)
                        },
                        onRegister = {
                            add(Destination.Register)
                        }
                    )

                }
                entry<Destination.Register> {
                    RegisterScreen(
                        navigateToHome = {
                            removeLastOrNull()
                            add(Destination.Main)
                        },
                        onBackClick = {
                            removeLastOrNull()
                        }
                    )
                }
                entry<Destination.Settings> {
                    SettingsScreen(
                        onNavigationBack = {
                            removeLastOrNull()
                        },
                        onShowLanguageBottomSheet = {
                            add(Destination.LanguageBottomSheet)
                        }
                    )
                }
                entry<Destination.Main> {
                    MainScreen(
                        navigateToPersonInfo = {

                        },
                        navigateToSettings = {
                            add(Destination.Settings)
                        },
                        navigateToHelpCenter = {},
                        logout = {
//                            clear()
                            add(Destination.Login)
                        }
                    )
                }
                entry<Destination.LanguageBottomSheet>(
                    metadata = BottomSheetSceneStrategy.bottomSheet()
                ) {
                    LanguageBottomSheet()
                }
            },
            transitionSpec = {
                // Slide in from right when navigating forward
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(1000)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(1000)
                )
            },
            popTransitionSpec = {
                // Slide in from left when navigating back
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(1000)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(1000)
                )
            },
            predictivePopTransitionSpec = {
                // Slide in from left when navigating back
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(1000)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(1000)
                )
            }
        )
    }

}


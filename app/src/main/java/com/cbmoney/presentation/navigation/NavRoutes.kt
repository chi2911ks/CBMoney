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
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.presentation.buget.BudgetSettingsScreen
import com.cbmoney.presentation.category.AddCategoryScreen
import com.cbmoney.presentation.category.CategoriesScreen
import com.cbmoney.presentation.category.EditCategoryScreen
import com.cbmoney.presentation.forgotpassword.ForgotPasswordScreen
import com.cbmoney.presentation.login.LoginScreen
import com.cbmoney.presentation.main.MainNavigator
import com.cbmoney.presentation.main.MainScreen
import com.cbmoney.presentation.onboarding.OnBoardingScreen
import com.cbmoney.presentation.register.RegisterScreen
import com.cbmoney.presentation.settings.LanguageBottomSheet
import com.cbmoney.presentation.settings.SettingsScreen
import com.cbmoney.presentation.splash.SplashScreen
import com.cbmoney.presentation.transaction.AddTransactionScreen
import com.cbmoney.presentation.transaction.TransactionListScreen
import com.cbmoney.utils.exts.clearAll


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavRoutes() {
    val bottomSheetStrategy = remember { BottomSheetSceneStrategy<NavKey>() }
    val backStack = rememberNavBackStack(Destination.Splash)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        sceneStrategy = bottomSheetStrategy,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Destination.Splash> {
                SplashScreen(
                    navigateToHome = {
                        backStack.clearAll()
                        backStack.add(Destination.Main)
                    },
                    navigateToOnBoarding = {
                        backStack.add(Destination.Onboarding)
                    },
                    navigateToSignIn = {
                        backStack.add(Destination.Login)
                    }
                )
            }
            entry<Destination.Onboarding> {
                OnBoardingScreen(onContinueClicked = {
                    backStack.add(Destination.Login)
                })
            }
            entry<Destination.Login> {
                LoginScreen(
                    navigateToHome = {
                        backStack.clearAll()
                        backStack.add(Destination.Main)
                    },
                    onRegister = {
                        backStack.add(Destination.Register)
                    },
                    navigateToForgotPassword = {
                        backStack.add(Destination.ForgotPassword)
                    }
                )

            }
            entry<Destination.ForgotPassword> {
                ForgotPasswordScreen(
                    onBackNavigation = {
                        backStack.removeLastOrNull()
                    }
                )
            }
            entry<Destination.Register> {
                RegisterScreen(
                    navigateToHome = {
                        backStack.clearAll()
                        backStack.add(Destination.Main)
                    },
                    onBackClick = {
                        backStack.removeLastOrNull()
                    }
                )
            }
            entry<Destination.Settings> {
                SettingsScreen(
                    onNavigationBack = {
                        backStack.removeLastOrNull()
                    },
                    onShowLanguageBottomSheet = {
                        backStack.add(Destination.LanguageBottomSheet)
                    }
                )
            }
            entry<Destination.Main> {
                MainScreen(
                    navigator = MainNavigator(
                        toPersonInfo = {

                        },
                        toSettings = {
                            backStack.add(Destination.Settings)
                        },
                        toHelpCenter = {},
                        toTransaction = {
                            backStack.add(Destination.AddTransaction(it))
                        },
                        toLogout = {
                            backStack.clearAll()
                            backStack.add(Destination.Login)
                        },
                        toBack = {
                            backStack.removeLastOrNull()
                        },
                        toBudgetSettings = {
                            backStack.add(Destination.BudgetSettings)
                        },
                        toTransactionList = {
                            backStack.add(Destination.TransactionList)
                        }
                    )
                )
            }
            entry<Destination.LanguageBottomSheet>(
                metadata = BottomSheetSceneStrategy.bottomSheet()
            ) {
                LanguageBottomSheet()
            }
            entry<Destination.AddTransaction> {
                AddTransactionScreen(
                    currentType = it.type,
                    onBackNavigation = {
                        backStack.removeLastOrNull()
                    },
                    navigateToCategory = { type ->
                        backStack.add(Destination.Categories(type))
                    },
                    navigateToTransactionList = {
                        backStack.add(Destination.TransactionList)
                    }
                )
            }
            entry<Destination.TransactionList> {
                TransactionListScreen(
                    onBackNavigation = { backStack.removeLastOrNull() },
                    navigateToAddTransaction = {
                        backStack.add(Destination.AddTransaction(CategoryType.EXPENSE))
                    }
                )

            }
            entry<Destination.Categories> {
                CategoriesScreen(
                    categoryCurrent = it.type,
                    onBackNavigation = {
                        backStack.removeLastOrNull()
                    },
                    navigateToAddCategory = { type ->
                        backStack.add(Destination.AddCategory(type))
                    },
                    navigateToEditCategory = { cate ->
                        backStack.add(Destination.EditCategory(cate))
                    }
                )
            }
            entry<Destination.AddCategory> {
                AddCategoryScreen(
                    currentType = it.type,
                    onBackNavigation = {
                        backStack.removeLastOrNull()
                    }
                )
            }
            entry<Destination.EditCategory> {
                EditCategoryScreen(
                    category = it.category,
                    onBackNavigation = {
                        backStack.removeLastOrNull()
                    }
                )
            }
            entry<Destination.BudgetSettings> {
                BudgetSettingsScreen(
                    onBackNavigation = {
                        backStack.removeLastOrNull()
                    },
                    navigateToAddCategory = {
                        backStack.add(Destination.AddCategory())
                    }
                )
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


package com.cbmoney.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cbmoney.R
import com.cbmoney.presentation.buget.BudgetScreen
import com.cbmoney.presentation.home.HomeScreen
import com.cbmoney.presentation.login.LoginScreen
import com.cbmoney.presentation.onboarding.OnBoardingScreen
import com.cbmoney.presentation.profile.ProfileScreen
import com.cbmoney.presentation.register.RegisterScreen
import com.cbmoney.presentation.reports.ReportScreen

sealed class NavDestination(val route: String) {
    sealed class AuthFlow(route: String) : NavDestination(route) {
        object OnBoarding : AuthFlow("/onboarding")
        object Register : AuthFlow("/register")
        object Login : AuthFlow("/login")
    }

    sealed class BottomNavItem(
        route: String,
        @StringRes val labelResId: Int,
        @DrawableRes val iconResId: Int
    ) : NavDestination(route) {
        object Home : BottomNavItem("/home", R.string.home, R.drawable.ic_home)
        object Reports : BottomNavItem("/reports", R.string.reports, R.drawable.ic_report)
        object Budget : BottomNavItem("/budget", R.string.budget, R.drawable.ic_wallet)
        object Profile : BottomNavItem("/profile", R.string.profile, R.drawable.ic_profile)
    }

    // Các loại đích đến khác (ví dụ: màn hình chi tiết)
    // object DetailScreen : NavDestination("/detail/{id}") {
    //     fun createRoute(id: String) = "/detail/$id"
    // }
}

@Composable
fun AppNavigation() {
    var isVisibility by rememberSaveable { mutableStateOf(false) }
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        BottomNavigation(navController, isVisibility = isVisibility)
    })
    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavDestination.AuthFlow.OnBoarding.route
        ) {
            composable(route = NavDestination.AuthFlow.OnBoarding.route) {
                isVisibility = false
                OnBoardingScreen(
                    modifier = Modifier.padding(innerPadding)
                )
                {
                    navController.navigate(NavDestination.AuthFlow.Login.route)
                }
            }
            composable(route = NavDestination.AuthFlow.Login.route) {
                isVisibility = false
                LoginScreen(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    navController.navigate(NavDestination.AuthFlow.Register.route)
                }
            }
            composable(route = NavDestination.AuthFlow.Register.route) {
                isVisibility = false
                RegisterScreen(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    navController.navigate(NavDestination.BottomNavItem.Home.route)
                }
            }
            composable(route = NavDestination.BottomNavItem.Home.route) {
                isVisibility = true
                HomeScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable(route = NavDestination.BottomNavItem.Reports.route) {
                isVisibility = true
                ReportScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable(route = NavDestination.BottomNavItem.Budget.route) {
                isVisibility = true
                BudgetScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable(route = NavDestination.BottomNavItem.Profile.route) {
                isVisibility = true
                ProfileScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }

        }
    }

}
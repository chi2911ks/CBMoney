package com.cbmoney.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cbmoney.R
import com.cbmoney.presentation.home.HomeScreen
import com.cbmoney.presentation.login.LoginScreen
import com.cbmoney.presentation.onboarding.OnBoardingScreen
import com.cbmoney.presentation.register.RegisterScreen

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

sealed class NavItem(var labelRes: Int, var iconRes: Int, var route: String) {
    object OnBoarding : NavItem(0, 0, "/onboarding")
    object Register : NavItem(0, 0, "/register")
    object Login : NavItem(0, 0, "/login")

    object Home : NavItem(R.string.home, R.drawable.ic_home, "/home")
    object Reports: NavItem(R.string.reports, R.drawable.ic_report, "/reports")
    object Budget: NavItem(R.string.budget, R.drawable.ic_wallet, "/budget")
    object Profile: NavItem(R.string.profile, R.drawable.ic_profile, "/profile")
}
@Composable
fun NavController(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavDestination.AuthFlow.OnBoarding.route){
        composable(route = NavDestination.AuthFlow.OnBoarding.route){
            OnBoardingScreen{
                navController.navigate(NavDestination.AuthFlow.Login.route)
            }
        }
        composable(route = NavDestination.AuthFlow.Login.route){
            LoginScreen{
                navController.navigate(NavDestination.AuthFlow.Register.route)
            }
        }
        composable(route = NavDestination.AuthFlow.Register.route) {
            RegisterScreen{
                navController.navigate(NavDestination.BottomNavItem.Home.route)
            }
        }
        composable(route = NavDestination.BottomNavItem.Home.route) {
            HomeScreen(navController)
        }
    }
}
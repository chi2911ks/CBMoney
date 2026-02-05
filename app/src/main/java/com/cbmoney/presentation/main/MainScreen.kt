package com.cbmoney.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.presentation.buget.BudgetScreen
import com.cbmoney.presentation.home.HomeScreen
import com.cbmoney.presentation.main.components.BottomNavBar
import com.cbmoney.presentation.main.model.MainTab
import com.cbmoney.presentation.profile.ProfileScreen
import com.cbmoney.presentation.reports.ReportScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    navigator: MainNavigator,
    viewModel: MainViewModel = koinViewModel()
) {
//    var currentTab by remember { mutableStateOf(MainTab.HOME) }
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ){
            when (viewState.currentTab) {
                MainTab.HOME -> HomeScreen(
                    navigateToReport = { viewModel.handleNavigateToTab(MainTab.REPORTS) },
                    navigateToTransaction = {
                        navigator.toTransaction(it)
                    }
                )

                MainTab.REPORTS -> ReportScreen()
                MainTab.BUDGET -> BudgetScreen(
                    openBudgetSettings = navigator.toBudgetSettings
                )

                MainTab.PROFILE -> ProfileScreen(
                    navigateToPersonInfo = navigator.toPersonInfo,
                    navigateToSettings = navigator.toSettings,
                    navigateToHelpCenter = navigator.toHelpCenter,
                    logout = navigator.toLogout
                )
            }
        }
        BottomNavBar(
            currentTab = viewState.currentTab,
            onTabChange = { newTab ->
                viewModel.processIntent(MainIntent.NavigateTab(newTab))
            },
            {navigator.toTransaction(CategoryType.EXPENSE)},
            modifier = Modifier
//                .align(Alignment.BottomCenter)
        )
    }


}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        navigator = MainNavigator(
            toPersonInfo = {},
            toSettings = {},
            toHelpCenter = {},
            toTransaction = {},
            toLogout = {},
            toBack = {},
            toBudgetSettings = {}
        ),
        MainViewModel()
    )
}
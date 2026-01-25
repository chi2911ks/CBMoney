package com.cbmoney.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.presentation.buget.BudgetScreen
import com.cbmoney.presentation.home.HomeScreen
import com.cbmoney.presentation.main.components.BottomNavBar
import com.cbmoney.presentation.main.model.MainTab
import com.cbmoney.presentation.profile.ProfileScreen
import com.cbmoney.presentation.reports.ReportScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    navigateToPersonInfo: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToHelpCenter: () -> Unit,
    navigateToTransaction: () -> Unit,
    logout: () -> Unit,
    viewModel: MainViewModel = koinViewModel()
) {
//    var currentTab by remember { mutableStateOf(MainTab.HOME) }
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (viewState.currentTab) {
            MainTab.HOME -> HomeScreen(
                navigateToReport = { viewModel.handleNavigateToTab(MainTab.REPORTS) }
            )

            MainTab.REPORTS -> ReportScreen()
            MainTab.BUDGET -> BudgetScreen()
            MainTab.PROFILE -> ProfileScreen(
                navigateToPersonInfo = navigateToPersonInfo,
                navigateToSettings = navigateToSettings,
                navigateToHelpCenter = navigateToHelpCenter,
                logout = {
                    logout()
                }
            )
        }
        BottomNavBar(
            currentTab = viewState.currentTab,
            onTabChange = { newTab -> viewModel.processIntent(MainIntent.NavigateTab(newTab)) },
            navigateToTransaction,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }


}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen({}, {}, {}, {},{})
}
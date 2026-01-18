package com.cbmoney.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.presentation.buget.BudgetScreen
import com.cbmoney.presentation.home.HomeScreen
import com.cbmoney.presentation.main.components.BottomNavBar
import com.cbmoney.presentation.main.model.MainTab
import com.cbmoney.presentation.profile.ProfileScreen
import com.cbmoney.presentation.reports.ReportScreen

@Composable
fun MainScreen() {
    var currentTab by remember { mutableStateOf(MainTab.HOME) }
    Box (
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            when (currentTab) {
                MainTab.HOME -> HomeScreen()
                MainTab.REPORTS -> ReportScreen()
                MainTab.BUDGET -> BudgetScreen()
                MainTab.PROFILE -> ProfileScreen()
            }
        }
        BottomNavBar(
            currentTab = currentTab,
            onTabChange = { newTab -> currentTab = newTab },
            {},
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
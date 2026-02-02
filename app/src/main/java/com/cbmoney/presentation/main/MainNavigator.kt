package com.cbmoney.presentation.main

data class MainNavigator(
    val toPersonInfo: () -> Unit,
    val toSettings: () -> Unit,
    val toHelpCenter: () -> Unit,
    val toTransaction: () -> Unit,
    val toLogout: () -> Unit,
    val toBack: () -> Unit,
    val toBudgetSettings: () -> Unit
)

package com.cbmoney.presentation.main

import com.cbmoney.domain.model.CategoryType

data class MainNavigator(
    val toPersonInfo: () -> Unit,
    val toSettings: () -> Unit,
    val toHelpCenter: () -> Unit,
    val toTransaction: (CategoryType) -> Unit,
    val toTransactionList: () -> Unit,
    val toLogout: () -> Unit,
    val toBack: () -> Unit,
    val toBudgetSettings: () -> Unit
)

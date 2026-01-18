package com.cbmoney.presentation.main.model

import androidx.compose.ui.res.stringResource
import com.cbmoney.R

enum class MainTab(
    val iconResId: Int,
    val title: String
) {
    HOME(iconResId = R.drawable.ic_home, title = "Home"),
    REPORTS(iconResId = R.drawable.ic_report, title = "Reports"),
    BUDGET(iconResId = R.drawable.ic_wallet, title = "Budget"),
    PROFILE(iconResId = R.drawable.ic_profile, title = "Profile")
}
package com.cbmoney.presentation.buget

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.R
import com.cbmoney.presentation.buget.components.BudgetCategoryItem
import com.cbmoney.presentation.buget.components.YearMonthSelector
import com.cbmoney.presentation.buget.contract.BudgetIntent
import com.cbmoney.presentation.buget.contract.BudgetSettingsIntent
import com.cbmoney.presentation.buget.contract.BudgetState
import com.cbmoney.presentation.buget.viewmodel.BudgetViewModel
import com.cbmoney.presentation.components.CircularProgressBar
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.formatMoney
import com.cbmoney.utils.exts.shadowCustom
import org.koin.androidx.compose.koinViewModel

@Composable
fun BudgetScreen(
    openBudgetSettings: () -> Unit,
    viewModel: BudgetViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()

    BudgetScreenContent(
        uiState,
        openBudgetSettings = openBudgetSettings,
        processIntent = viewModel::processIntent
    )
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BudgetScreenContent(
    uiState: BudgetState,
    openBudgetSettings: () -> Unit,
    processIntent: (BudgetIntent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .padding(horizontal = Spacing.md)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) {
                Text(
                    text = stringResource(R.string.budget_management),
                    style = CBMoneyTypography.Body.Large.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(Spacing.md))
            YearMonthSelector(
                yearMonth = uiState.currentMonth,
                onYearMonthChange = { newYearMonth ->
                    processIntent(BudgetIntent.OnChangeCurrentMonth(newYearMonth))
                }
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadowCustom()
                    .background(CBMoneyColors.White, CBMoneyShapes.extraLarge)
                    .padding(vertical = Spacing.md),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.expense_statistics),
                    style = CBMoneyTypography.Title.Large.Bold
                )
                Spacer(modifier = Modifier.height(Spacing.md))
                CircularProgressBar(
                    size = 140.dp,
                    progress = uiState.totalBudget?.percentage ?: 0f,
                    description = stringResource(R.string.spent)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(Spacing.md),
                    color = Color(0xFFE0E0E0),
                    thickness = 1.dp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.budget),
                            style = CBMoneyTypography.Body.Medium.Regular
                        )
                        Text(
                            text = "${uiState.totalBudget?.amount?.formatMoney() ?: 0} đ",
                            style = CBMoneyTypography.Title.Small.Bold
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.spent),
                            style = CBMoneyTypography.Body.Medium.Regular
                        )
                        Text(
                            text = "${uiState.totalBudget?.spent?.formatMoney()?: 0} đ",
                            style = CBMoneyTypography.Title.Small.Bold
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.remaining),
                            style = CBMoneyTypography.Body.Medium.Regular
                        )
                        Text(
                            text = "${uiState.totalBudget?.remaining?.formatMoney()?: 0} đ",
                            style = CBMoneyTypography.Title.Small.Bold
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(Spacing.md))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.budget_by_category),
                    style = CBMoneyTypography.Body.Large.Bold,
                )

            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = Spacing.sm),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                items(
                    items = uiState.budgetsByCategory,
                    key = { it.budget.id }
                ) {
                    BudgetCategoryItem(
                        name = it.categoryName ?: "",
                        iconColor = it.iconColor ?: "",
                        icon = it.categoryIcon ?: "",
                        budgetAmount = it.budget.amount,
                        spentAmount = it.budget.spent
                    )
                }
            }


        }
        IconButton(
            onClick = openBudgetSettings,
            modifier = Modifier
                .align(Alignment.BottomEnd),
            shape = CircleShape,
            colors = androidx.compose.material3.IconButtonDefaults.iconButtonColors(
                containerColor = CBMoneyColors.Primary.Primary,
                contentColor = CBMoneyColors.White
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
private fun BudgetContentPreview() {

}
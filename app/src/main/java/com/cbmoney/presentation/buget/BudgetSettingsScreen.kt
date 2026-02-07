package com.cbmoney.presentation.buget

import android.R.attr.category
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.R
import com.cbmoney.presentation.buget.components.ExpenditureCategoryItem
import com.cbmoney.presentation.buget.components.YearMonthSelector
import com.cbmoney.presentation.buget.contract.BudgetSettingsEvent
import com.cbmoney.presentation.buget.contract.BudgetSettingsIntent
import com.cbmoney.presentation.buget.contract.BudgetSettingsState
import com.cbmoney.presentation.buget.viewmodel.BudgetSettingsViewModel
import com.cbmoney.presentation.components.ButtonPrimary
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.formatDigit
import com.cbmoney.utils.exts.formatMoney
import com.cbmoney.utils.exts.rawClickable
import com.cbmoney.utils.exts.shadowCustom
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BudgetSettingsScreen(
    onBackNavigation: () -> Unit,
    navigateToAddCategory: () -> Unit,
    viewModel: BudgetSettingsViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()

//    LaunchedEffect(uiState.currentMonth) {
//        viewModel.processIntent(BudgetSettingsIntent.LoadBudget)
//    }
    LaunchedEffect(Unit) {
        viewModel.singleEvent.collect {
            when (it) {
                is BudgetSettingsEvent.Close -> onBackNavigation()
                is BudgetSettingsEvent.SaveError -> {

                }
            }
        }
    }
    BudgetSettingsContent(
        uiState,
        onBackNavigation = onBackNavigation,
        navigateToAddCategory = navigateToAddCategory,
        viewModel::processIntent
    )
}


@Composable
fun BudgetSettingsContent(
    uiState: BudgetSettingsState,
    onBackNavigation: () -> Unit,
    navigateToAddCategory: () -> Unit,
    processIntent: (BudgetSettingsIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .statusBarsPadding()
            .padding(horizontal = Spacing.md)
            .padding(top = Spacing.md)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .rawClickable {
                        onBackNavigation()
                    }
            )
            Text(
                text = stringResource(R.string.budget_settings),
                style = CBMoneyTypography.Body.Large.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
            Text(
                text = stringResource(R.string.save),
                color = CBMoneyColors.Primary.Primary,
                style = CBMoneyTypography.Body.Large.Bold,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .rawClickable {
                        processIntent(BudgetSettingsIntent.OnSaveBudget)
                    }

            )

        }
        Spacer(modifier = Modifier.height(Spacing.md))
        YearMonthSelector(
            yearMonth = uiState.currentMonth,
            onYearMonthChange = { newYearMonth ->
                processIntent(BudgetSettingsIntent.OnChangeCurrentMonth(newYearMonth))
            }
        )
        Spacer(modifier = Modifier.height(Spacing.md))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadowCustom()
                .background(CBMoneyColors.White, CBMoneyShapes.extraLarge)
                .padding(Spacing.md),
        ) {
            Text(
                text = stringResource(R.string.total_budget).uppercase(),
                style = CBMoneyTypography.Body.Large.Bold,
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = if (uiState.totalBudget?.amount == 0L) "" else uiState.totalBudget?.amount?.formatMoney()
                        ?: "",
                    onValueChange = {
                        processIntent(
                            BudgetSettingsIntent.OnChangeTotalBudget(
                                it.formatDigit() ?: 0L
                            )
                        )

                    },
                    textStyle = CBMoneyTypography.Title.Large.Bold.copy(
                        color = CBMoneyColors.Primary.Primary
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    decorationBox = { innerTextField ->
                        if (uiState.totalBudget?.amount == 0L) {
                            Text(
                                text = stringResource(R.string.not_set),
                                style = CBMoneyTypography.Title.Large.Regular.copy(
                                    color = CBMoneyColors.Neutral.NeutralGray,
                                    fontStyle = FontStyle.Italic
                                ),

                                )
                        }
                        innerTextField()
                    },
                    modifier = Modifier.weight(1f)
                )
                if (uiState.totalBudget?.amount != 0L) {
                    Text(
                        text = "đ",
                        style = CBMoneyTypography.Title.Large.Bold.copy(
                            color = CBMoneyColors.Primary.Primary
                        ),
                    )
                }
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = CBMoneyColors.Neutral.NeutralGray.copy(0.5f)
            )
            Spacer(modifier = Modifier.height(Spacing.md))
            Text(
                text = stringResource(R.string.allocation_details),
                style = CBMoneyTypography.Body.Medium.Bold,
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${stringResource(R.string.total_budget_category)}: ",
                    style = CBMoneyTypography.Body.Small.Regular,
                )
//                val totalBudget = uiState.budgetsAmount.values.sumOf { it["amount"] as Long }
                val totalBudget = 0L
                Text(
                    text = totalBudget.formatMoney(),
                    style = CBMoneyTypography.Body.Small.Bold,
                )
            }
        }
        Spacer(modifier = Modifier.height(Spacing.md))
        ExpenditureCategory(
            uiState = uiState,
            navigateToAddCategory = navigateToAddCategory,
            processIntent = processIntent
        )
    }

}





@Composable
fun ExpenditureCategory(
    modifier: Modifier = Modifier,
    uiState: BudgetSettingsState,
    navigateToAddCategory: () -> Unit,
    processIntent: (BudgetSettingsIntent) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.budget_by_category),
            modifier = Modifier.padding(horizontal = Spacing.sm),
            style = CBMoneyTypography.Body.Large.Bold
        )
        Spacer(modifier = Modifier.height(Spacing.sm))
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(Spacing.sm),
        ){
            items(
                items = uiState.budgetsCategory.toList(),
                key = { it.first }
            ) { category ->
                val categoryId = category.first
                val budget = uiState.budgetsCategory[categoryId]?.budget?.amount
//                val budget = uiState.budgetsAmount.getOrDefault(categoryId, mapOf("amount" to 0L))
//                    .getOrDefault("amount", 0L)
                ExpenditureCategoryItem(
                    budget = if (budget == 0L) "" else budget.toString(),
                    onBudgetChange = {
                        processIntent(
                            BudgetSettingsIntent.OnChangeBudget(
                                categoryId = categoryId,
                                budget = if (it.isEmpty()) 0L else it.toLong()
                            )
                        )
                    },
                    iconColor = category.second.iconColor?:"",
                    name = category.second.categoryName?:"",
                    icon = category.second.categoryIcon?:""
                )
            }
            item {
                Spacer(modifier = Modifier.height(Spacing.md))
                ButtonPrimary(
                    text = stringResource(R.string.add_category),
                    onClick = {
                        navigateToAddCategory()
                    },
                    colors  = ButtonDefaults.buttonColors(
                        containerColor = CBMoneyColors.Primary.Primary.copy(0.2f),
                        contentColor = CBMoneyColors.Black
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.xl),
                )
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun BudgetSettingsBottomSheetPreview() {
    BudgetSettingsScreen(
        onBackNavigation = {},
        navigateToAddCategory = {}
    )
//
}

package com.cbmoney.presentation.buget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.R
import com.cbmoney.domain.constants.DefaultCategories
import com.cbmoney.presentation.buget.components.BudgetCategoryItem
import com.cbmoney.presentation.components.CircularProgressBar
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing

@Composable
fun BudgetScreen(

) {
    BudgetScreenContent()
}

@Composable
fun BudgetScreenContent(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .padding(horizontal = Spacing.md)
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
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Spacer(modifier = Modifier.height(Spacing.md))
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = CBMoneyShapes.extraLarge,
            colors = CardDefaults.cardColors(
                containerColor = CBMoneyColors.White
            ),
            elevation = CardDefaults.cardElevation(0.dp),
            border = BorderStroke(1.dp, CBMoneyColors.Border.BorderBland)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Spacing.md),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Thống kê chi tiêu",
                    style = CBMoneyTypography.Title.Large.Bold
                )
                Spacer(modifier = Modifier.height(Spacing.md))
                CircularProgressBar(
                    progress = 0.33f,
                    description = "Đã chi"
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
                            text = "Ngân sách",
                            style = CBMoneyTypography.Body.Medium.Regular
                        )
                        Text(
                            text = "10.000.000 đ",
                            style = CBMoneyTypography.Title.Small.Bold
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Đã chi",
                            style = CBMoneyTypography.Body.Medium.Regular
                        )
                        Text(
                            text = "100.000 đ",
                            style = CBMoneyTypography.Title.Small.Bold
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Còn lại",
                            style = CBMoneyTypography.Body.Medium.Regular
                        )
                        Text(
                            text = "10.000.000 đ",
                            style = CBMoneyTypography.Title.Small.Bold
                        )
                    }
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
                text = "Ngân sách theo mục",
                style = CBMoneyTypography.Body.Large.Bold,
            )
            Text(
                text = stringResource(R.string.see_all),
                style = CBMoneyTypography.Body.Medium.Medium,
                textDecoration = TextDecoration.Underline,
                color = CBMoneyColors.Primary.Primary,
                modifier = Modifier
                    .clickable {

                    }
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = Spacing.sm),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            item {
                BudgetCategoryItem(
                    category = DefaultCategories.INCOME_CATEGORIES[0],
                    budgetAmount = 2000000,
                    spentAmount =  1000000
                )
            }
            item {
                BudgetCategoryItem(
                    category = DefaultCategories.INCOME_CATEGORIES[1],
                    budgetAmount = 2000000,
                    spentAmount =  3000000
                )
            }
            item {
                BudgetCategoryItem(
                    category = DefaultCategories.INCOME_CATEGORIES[2],
                    budgetAmount = 2000000,
                    spentAmount =  500000
                )
            }
        }


    }
}

@Preview
@Composable
private fun BudgetContentPreview() {
    BudgetScreenContent()
}
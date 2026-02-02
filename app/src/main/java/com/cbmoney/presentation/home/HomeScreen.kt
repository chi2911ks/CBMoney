package com.cbmoney.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.R
import com.cbmoney.presentation.components.ButtonWithIcon
import com.cbmoney.presentation.home.components.CarMoney
import com.cbmoney.presentation.home.components.FinanceCard
import com.cbmoney.presentation.home.components.MonthlySpendingCard
import com.cbmoney.presentation.home.components.model.MonthlyData
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    navigateToReport: () -> Unit,
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val uiState by homeViewModel.viewState.collectAsStateWithLifecycle()
    HomeScreenContent(
        uiState,
        navigateToReport = navigateToReport
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeState,
    navigateToReport: () -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val listData = listOf(
        MonthlyData(stringResource(R.string.month_1), 10000000L, 20000000L),
        MonthlyData(stringResource(R.string.month_2), 7000000L, 2000000L),
        MonthlyData(stringResource(R.string.month_3), 2000000L, 1000000L),
        MonthlyData(stringResource(R.string.month_4), 10000000L, 20000000L),
        MonthlyData(stringResource(R.string.month_5), 3000000L, 200000L),
        MonthlyData(stringResource(R.string.month_6), 2000000L, 1000000L),
        MonthlyData(stringResource(R.string.month_7), 10000000L, 2000000L),
        MonthlyData(stringResource(R.string.month_8), 1000000L, 200000L),
        MonthlyData(stringResource(R.string.month_9), 7000000L, 10000000L),
        MonthlyData(stringResource(R.string.month_10), 9000000L, 20000000L),
        MonthlyData(stringResource(R.string.month_11), 6000000L, 2000000L),
        MonthlyData(stringResource(R.string.month_12), 26000000L, 10000000L),
    )
    val income = listData[selectedIndex].income
    val expense = listData[selectedIndex].expense

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .statusBarsPadding()
            .padding(horizontal = Spacing.md)
//            .padding(bottom = 70.dp)
            .verticalScroll(rememberScrollState())
        ,

    ) {

        HeaderSection(
            user = uiState.user?.name?.ifEmpty { uiState.user.email.split('@')[0] } ?: "",
            onClickProfile = {},
            onClickNotification = {}
        )
        CarMoney(
            onClick = {},
            Modifier
                .fillMaxWidth()
                .padding(top = Spacing.sm)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FinanceCard(
                modifier = Modifier.weight(1f),
                money = income
            )
            Spacer(Modifier.width(Spacing.sm))
            FinanceCard(
                modifier = Modifier.weight(1f),
                isIncome = false,
                money = expense
            )
        }
        MonthlySpendingCard(
            selectedIndex,
            { selectedIndex = it },
            {navigateToReport()},
            listData,
            Modifier
                .padding(top = Spacing.sm)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Spacing.sm),
        ) {
            ButtonWithIcon(
                modifier = Modifier
                    .weight(1f),
                text = stringResource(R.string.additional_expenses),
                onClick = {},
                iconVector = Icons.Default.Add
            )
            Spacer(Modifier.width(Spacing.sm))
            ButtonWithIcon(
                modifier = Modifier
                    .weight(1f),
                text = stringResource(R.string.additional_income),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = CBMoneyColors.White,
                    contentColor = CBMoneyColors.Text.TextPrimary,
                ),
                iconVector = Icons.Default.AttachMoney,
                tint = CBMoneyColors.Black
            )

        }
    }

}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    user: String = "Đỗ Chi",
    onClickProfile: () -> Unit = {},
    onClickNotification: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Transparent)
                    .size(40.dp)
                    .clickable {
                        onClickProfile()
                    },
                contentAlignment = Alignment.Center
            ){
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)

                )
            }

            Spacer(Modifier.width(Spacing.sm))
            Column {
                Text(
                    text = stringResource(R.string.hello_user),
                    color = CBMoneyColors.Neutral.NeutralGray,
                    style = CBMoneyTypography.Title.Small.Regular
                )
                Text(
                    text = user,
                    color = Color.Black,
                    style = CBMoneyTypography.Title.Medium.Medium
                )
            }

        }


        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .background(Color.Transparent)
                .size(40.dp)
                .clickable {
                    onClickNotification()
                },

            contentAlignment = Alignment.Center

        ) {
            Icon(
                Icons.Default.Notifications,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(36.dp)

            )
        }

    }
}

@Preview
@Composable
private fun HomeScreenPreview() {

        HomeScreen({})

}
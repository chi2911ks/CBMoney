package com.cbmoney.presentation.home

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.cbmoney.R
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.domain.model.User
import com.cbmoney.presentation.components.button.ButtonWithIcon
import com.cbmoney.presentation.home.components.FinanceCard
import com.cbmoney.presentation.home.components.MonthlyData
import com.cbmoney.presentation.home.components.MonthlySpendingCard
import com.cbmoney.presentation.home.components.RecentTransactionItem

import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.shadowCustom
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    navigateToReport: () -> Unit,
    navigateToTransaction: (CategoryType) -> Unit,
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val uiState by homeViewModel.viewState.collectAsStateWithLifecycle()
    HomeScreenContent(
        uiState,
        navigateToReport = navigateToReport,
        navigateToTransaction = navigateToTransaction
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeState,
    navigateToReport: () -> Unit,
    navigateToTransaction: (CategoryType) -> Unit,
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

        ,

    ) {

        HeaderSection(
            user = uiState.user,
            onClickProfile = {},
            onClickNotification = {}
        )
//        CarMoney(
//            onClick = {},
//            Modifier
//                .fillMaxWidth()
//                .padding(top = Spacing.sm)
//        )
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
                    .shadowCustom()
                    .weight(1f),
                text = stringResource(R.string.additional_expenses),
                onClick = {
                    navigateToTransaction(CategoryType.EXPENSE)
                },
                iconVector = Icons.Default.Add
            )
            Spacer(Modifier.width(Spacing.sm))
            ButtonWithIcon(
                modifier = Modifier
                    .shadowCustom()
                    .weight(1f),
                text = stringResource(R.string.additional_income),
                onClick = {
                    navigateToTransaction(CategoryType.INCOME)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = CBMoneyColors.White,
                    contentColor = CBMoneyColors.Text.TextPrimary,
                ),
                iconVector = Icons.Default.AttachMoney,
                tint = CBMoneyColors.Black
            )

        }
        Spacer(Modifier.height(Spacing.sm))
        RecentTransactions(
            uiState = uiState
        )
    }

}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    user: User?,
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

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user?.photoUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = rememberVectorPainter(Icons.Default.AccountCircle),
                    error = rememberVectorPainter(Icons.Default.AccountCircle),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
//                        .border(1.dp, Color.Gray, CircleShape)
                        .clickable { }
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
                    text = user?.name?.ifEmpty { user.email.split('@')[0] } ?: "",
                    color = Color.Black,
                    style = CBMoneyTypography.Title.Medium.Medium
                )
            }

        }


        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(40.dp)
                .shadowCustom()
                .background(Color.White, CircleShape)

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
                    .size(24.dp)

            )
        }

    }
}


@Composable
fun RecentTransactions(
    modifier: Modifier = Modifier,
    uiState: HomeState = HomeState()
)
 {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.recent_transactions),
            style = CBMoneyTypography.Body.Large.Bold,
        )

        Text(
            text = stringResource(R.string.see_all),
            color = CBMoneyColors.Primary.Primary,
            style = CBMoneyTypography.Body.Medium.Regular
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(vertical = Spacing.sm),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
       items(
           items = uiState.transactions,
           key = { it.transaction.id }
       ){
           RecentTransactionItem(
               name = it.categoryName?:"",
               iconColor = it.iconColor?:"",
               icon = it.categoryIcon?:"",
               spent = it.transaction.amount,
               date = it.transaction.date
           )
       }
    }
}
@Preview
@Composable
private fun HomeScreenPreview() {

        HomeScreen({}, {})

}
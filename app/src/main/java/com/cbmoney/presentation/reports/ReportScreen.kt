package com.cbmoney.presentation.reports

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.R
import com.cbmoney.domain.model.CategorySpending
import com.cbmoney.presentation.components.button.ButtonPrimary
import com.cbmoney.presentation.components.view.BalanceSummary
import com.cbmoney.presentation.reports.components.CategoryProcessBar
import com.cbmoney.presentation.reports.components.CategorySpentItem
import com.cbmoney.presentation.reports.components.CategoryTabSelector
import com.cbmoney.presentation.reports.components.YearMonthSelector
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.dashedBorder
import com.cbmoney.utils.exts.formatMoney
import com.cbmoney.utils.exts.rawClickable
import com.cbmoney.utils.exts.shadowCustom
import com.cbmoney.utils.exts.toHex
import com.cbmoney.utils.fromPeriod
import org.koin.androidx.compose.koinViewModel
import java.time.YearMonth

@Composable
fun ReportScreen(
    viewModel: ReportViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit,
    onNavigateToTransaction: () -> Unit
) {
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()


    ReportScreenContent(
        uiState,
        processIntent = viewModel::processIntent,
        onNavigateToDetail = onNavigateToDetail,
        onNavigateToTransaction = onNavigateToTransaction
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReportScreenContent(
    uiState: ReportState,
    processIntent: (ReportIntent) -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToTransaction: () -> Unit
) {

    val verticalScroll = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .statusBarsPadding()
            .verticalScroll(verticalScroll)
            .padding(horizontal = Spacing.md)
            .padding(bottom = Spacing.md),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.reports),
                style = CBMoneyTypography.Body.Large.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
            Icon(
                painterResource(R.drawable.icon_share_ios),
                null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterEnd)
                    .rawClickable {}
            )


        }
        YearMonthSelector(
            yearMonth = uiState.yearMonth ?: YearMonth.now(),
            onYearMonthChange = {
                processIntent(ReportIntent.OnChangeCurrentMonth(it))
            }
        )
        CardExpenseIncome(uiState = uiState)
        Spacer(Modifier.height(Spacing.md))

        CardCategoryStats(
            uiState,
            processIntent,
            onNavigateToDetail = onNavigateToDetail,
            onNavigateToTransaction = onNavigateToTransaction
        )

    }

}

@Composable
fun CardExpenseIncome(
    modifier: Modifier = Modifier,
    uiState: ReportState,
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .shadowCustom()
            .background(CBMoneyColors.White, CBMoneyShapes.extraLarge)
            .padding(Spacing.md),
    ){
        Text(
            text = "Tiền thu chi",
            style = CBMoneyTypography.Title.Small.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
       Text(
           text = "${uiState.totalSummary.revenue.formatMoney()} đ",

           style = CBMoneyTypography.Title.Large.Bold.copy(
               textAlign = TextAlign.Center,
               color = if(uiState.totalSummary.revenue >0) CBMoneyColors.Green2 else CBMoneyColors.Red2
           ),
           modifier = Modifier.fillMaxWidth(),
       )
        Spacer(Modifier.height(Spacing.md))
        HorizontalDivider(
            thickness=1.dp,
            color = CBMoneyColors.Neutral.NeutralGray.copy(0.25f))
        Spacer(Modifier.height(Spacing.md))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            BalanceSummary(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.sm),
                isIncome = true,
                money = uiState.totalSummary.totalIncome
            )
            VerticalDivider(
                thickness=1.dp,
                color = CBMoneyColors.Neutral.NeutralGray.copy(0.25f)
            )
            BalanceSummary(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.sm),
                isIncome = false,
                money = uiState.totalSummary.totalExpense
            )
        }
    }
}


@Composable
fun CardCategoryStats(
    uiState: ReportState,
    processIntent: (ReportIntent) -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToTransaction: () -> Unit,
) {
    val filtered = uiState.spendingCategories.filter {
        it.type == fromPeriod(uiState.categoryType)
    }
    val listData = filtered.map {
        it.totalSpending to
                Color( it.iconColor?.toColorInt()
                    ?: Color.Gray.toHex().toColorInt())
    }
//    val month = uiState.yearMonth?.monthValue ?: 0
//
//    val sumSpent = listData.sumOf { it.first }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadowCustom()
            .background(CBMoneyColors.White, CBMoneyShapes.extraLarge),
        verticalArrangement = Arrangement.Center,
    ) {
        CategoryTabSelector(
            selected = uiState.categoryType,
            onSelectedChange = {
                processIntent(ReportIntent.OnChangeCategoryType(it))
            }
        )
        Spacer(Modifier.height(Spacing.md))
        Column (
            modifier = modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            verticalArrangement = Arrangement.Center,
        ){

            CategoryProcessBar(
                modifier = Modifier.fillMaxWidth(),
                listData = listData,
                categoryType = uiState.categoryType
            )
            Spacer(Modifier.height(Spacing.md))
            DetailReport(
                filtered,
                onNavigateToDetail = onNavigateToDetail,
                onNavigateToTransaction = onNavigateToTransaction
            )
        }

    }
}

@Composable
fun DetailReport(
    listData: List<CategorySpending>,
    modifier: Modifier = Modifier,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToTransaction: () -> Unit
) {
    val sumSpent = listData.sumOf { it.totalSpending }
    Text(
        text = stringResource(R.string.detailed_report).uppercase(),
        color = Color.Gray,
        style = CBMoneyTypography.Body.Small.Bold,
    )
    Spacer(modifier = Modifier.height(Spacing.md))
    LazyColumn(
        modifier = modifier
            .heightIn(max = 300.dp, min = 200.dp)
            .fillMaxWidth()
            .then(
                if (listData.isEmpty()) {
                    Modifier.background(
                        CBMoneyColors.Gray.Gray.copy(0.25f),
                        CBMoneyShapes.extraLarge
                    )
                } else {
                    Modifier
                }

            )
            .dashedBorder(
                color = Color(0xfff3f4f6),
                strokeWidth = 1.dp,
                cornerRadius = 28.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (listData.isEmpty()) {
            item {
                Spacer(modifier = Modifier.height(Spacing.md))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xfff3f4f6), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                        contentDescription = null,
                        tint = Color(0xffaab0bb),
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.height(Spacing.md))
                Text(
                    text = stringResource(R.string.add_transaction_to_view_report),
                    color = Color(0xff828993),
                    style = CBMoneyTypography.Body.Small.Bold,
                )
                Spacer(modifier = Modifier.height(Spacing.md))
                ButtonPrimary(
                    onClick = onNavigateToTransaction,
                    text = stringResource(R.string.add_transaction),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = CBMoneyColors.Green2,
                            modifier = Modifier.size(12.dp)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CBMoneyColors.Primary.Primary.copy(0.25f),
                        contentColor = CBMoneyColors.Green2
                    )
                )
                Spacer(modifier = Modifier.height(Spacing.md))
            }
        } else {
            items(listData.size) { index ->
                val item = listData[index]
                CategorySpentItem(
                    progress = item.totalSpending.toFloat() / sumSpent,
                    categorySpending = item,
                    onClick = {
                        item.categoryId?.let {
                            onNavigateToDetail(it)
                        }

                    }
                )
            }
        }
    }


}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun ReportScreenPreview() {
    ReportScreenContent(
        ReportState(), {}, {},
        onNavigateToTransaction = {}
    )

}
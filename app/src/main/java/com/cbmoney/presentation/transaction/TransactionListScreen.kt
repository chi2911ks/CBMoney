package com.cbmoney.presentation.transaction

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.R
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.presentation.components.dropdown.DropdownPrimary
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.presentation.transaction.components.TransactionItem
import com.cbmoney.presentation.transaction.contract.TransactionListIntent
import com.cbmoney.presentation.transaction.contract.TransactionListState
import com.cbmoney.presentation.transaction.viewmodel.TransactionListViewModel
import com.cbmoney.utils.exts.rawClickable
import com.cbmoney.utils.exts.toRelativeDateGroup
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TransactionListScreen(
    onBackNavigation: () -> Unit,
    navigateToAddTransaction: () -> Unit,
    viewModel: TransactionListViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()

    TransactionListScreenContent(
        uiState = uiState,
        onBackNavigation = onBackNavigation,
        navigateToAddTransaction = navigateToAddTransaction,
        processIntent = {
            viewModel.processIntent(it)
        }
    )
}

@Composable
fun TransactionListScreenContent(
    uiState: TransactionListState,
    onBackNavigation: () -> Unit,
    navigateToAddTransaction: () -> Unit,
    processIntent: (TransactionListIntent) -> Unit
) {
    val months = listOf(
        stringResource(R.string.str_month_1),
        stringResource(R.string.str_month_2),
        stringResource(R.string.str_month_3),
        stringResource(R.string.str_month_4),
        stringResource(R.string.str_month_5),
        stringResource(R.string.str_month_6),
        stringResource(R.string.str_month_7),
        stringResource(R.string.str_month_8),
        stringResource(R.string.str_month_9),
        stringResource(R.string.str_month_10),
        stringResource(R.string.str_month_11),
        stringResource(R.string.str_month_12)
    )
    val currentMonthIndex = java.time.LocalDate.now().monthValue - 1
    var monthSelected by remember { mutableStateOf(months[currentMonthIndex]) }
    var typeSelected by remember { mutableStateOf<CategoryType?>(null) }
    var cateSelected by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(monthSelected) {
        val monthNumber = months.indexOf(monthSelected) + 1
        processIntent(TransactionListIntent.LoadTransactions(monthNumber))
    }

    LaunchedEffect(typeSelected, cateSelected) {
        processIntent(TransactionListIntent.FilterTransactions(typeSelected, cateSelected))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .statusBarsPadding()
            .padding(horizontal = Spacing.md)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val horizontalScroll = rememberScrollState()
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .rawClickable {
                            onBackNavigation()
                        }
                )

                Text(
                    text = stringResource(R.string.str_transaction_list),
                    style = CBMoneyTypography.Body.Large.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .rawClickable {

                        }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(horizontalScroll)
                    .padding(top = Spacing.md),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                DropdownPrimary(
                    items = months,
                    selected = monthSelected,
                    itemLabel = { it.toString() },
                    onSelected = {
                        monthSelected = it
                    }
                )
                val categoryItems = mutableListOf<String?>(null)
                uiState.mapCategory[typeSelected]?.forEach {
                    categoryItems.add(it.name)
                }
                DropdownPrimary(
                    items = categoryItems,
                    selected = cateSelected,
                    itemLabel = {
                        (it ?: stringResource(R.string.str_categories))
                    },
                    onSelected = {
                        cateSelected = it
                    }
                )
                val categoryType = mutableListOf<CategoryType?>(null)

                for (type in uiState.mapCategory.keys) {
                    categoryType.add(type)
                }
                DropdownPrimary(
                    items = uiState.mapCategory.keys.toList(),
                    selected = typeSelected,
                    itemLabel = {
                        when (it) {
                            CategoryType.EXPENSE -> stringResource(R.string.str_expense)
                            CategoryType.INCOME -> stringResource(R.string.str_income)
                            else -> stringResource(R.string.str_type)
                        }

                    },
                    onSelected = {
                        if (typeSelected != it) {
                            typeSelected = it
                            cateSelected = null
                        }
                    }
                )
            }
            Spacer(Modifier.height(Spacing.sm))
            val context = LocalContext.current
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                uiState.transactions.forEach { (date, details) ->
                    item(key = "date-$date") {
                        Text(
                            text = date.toRelativeDateGroup(context).uppercase(),
                            style = CBMoneyTypography.Body.Medium.Bold,
                            modifier = Modifier.padding(vertical = Spacing.xs)
                        )
                    }
                    items(
                        items = details.toList(),
                        key = { it.transaction.id }
                    ) {
                        TransactionItem(
                            it.transaction,
                            it.categoryName,
                            it.categoryIcon,
                            it.iconColor,
                            it.transaction.date
                        )
                    }
                }
            }
            if (uiState.transactions.isEmpty()){
                EmptyTransaction()
            }
        }
        Box(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
        ) {
            IconButton(
                onClick = navigateToAddTransaction,
                modifier = Modifier
                    .size(48.dp)
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
}

@Composable
fun EmptyTransaction(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(CBMoneyColors.Primary.Primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.AutoMirrored.Filled.ReceiptLong,
                    contentDescription = null,
                    tint = CBMoneyColors.Primary.Primary
                )
            }
            Spacer(Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.str_no_transactions),
                style = CBMoneyTypography.Title.Medium.Bold,
                color = CBMoneyColors.Text.TextPrimary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.str_empty_transaction_body),
                style = CBMoneyTypography.Body.Medium.Regular,
                color = CBMoneyColors.Text.TextTertiary,
                textAlign = TextAlign.Center
            )
        }
    }
}
@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun TransactionListScreenPreview() {

}
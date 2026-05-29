package com.cbmoney.presentation.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material.icons.filled.Delete
import com.cbmoney.presentation.transaction.components.TabTransaction
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.R
import com.cbmoney.domain.model.Category
import com.cbmoney.domain.model.TransactionDetails
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.presentation.transaction.components.CategoryDropdown
import com.cbmoney.presentation.transaction.contract.TransactionListIntent
import com.cbmoney.presentation.transaction.contract.TransactionListState
import com.cbmoney.presentation.transaction.viewmodel.TransactionListViewModel
import com.cbmoney.utils.exts.formatMoney
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun TransactionListScreen(
    onBack: () -> Unit = {},
    onTransactionClick: (String) -> Unit = {}
) {
    val viewModel: TransactionListViewModel = koinViewModel()
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.processIntent(TransactionListIntent.LoadTransactions)
    }

    TransactionListScreenContent(
        state = uiState,
        onBack = onBack,
        onTransactionClick = onTransactionClick,
        onSearch = { query ->
            viewModel.processIntent(TransactionListIntent.SearchQueryChanged(query))
        },
        onCategorySelected = { category ->
            viewModel.processIntent(TransactionListIntent.SelectCategory(category))
        },
        onDelete = { transactionId ->
            viewModel.processIntent(TransactionListIntent.DeleteTransaction(transactionId))
        }
    )
}

@Composable
fun TransactionListScreenContent(
    state: TransactionListState,
    onBack: () -> Unit,
    onTransactionClick: (String) -> Unit,
    onSearch: (String) -> Unit,
    onCategorySelected: (Category?) -> Unit,
    onDelete: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .statusBarsPadding()
            .padding(horizontal = Spacing.md)
    ) {
        // Top bar
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { onBack() }
                    .padding(Spacing.sm)
            )
            Text(
                text = stringResource(R.string.transaction_list),
                style = CBMoneyTypography.Body.Large.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { /* TODO: implement search */ }
                    .padding(Spacing.sm)
            )
        }

        // Category filter row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Spacing.sm)
        ) {
            val categoryNames = listOf("Tất cả") + state.categories.map { it.name }
            var selected by remember { mutableStateOf(state.selectedCategory?.name ?: "Tất cả") }

            CategoryDropdown(
                categories = categoryNames,
                selected = selected,
                onSelected = { name ->
                    selected = name
                    val category = if (name == "Tất cả") null else state.categories.find { it.name == name }
                    onCategorySelected(category)
                }
            )
        }

        // Transaction list
        if (state.transactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_transactions),
                    style = CBMoneyTypography.Body.Large.Medium,
                    color = CBMoneyColors.Gray.Gray5
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Spacing.sm)
            ) {
                items(state.transactions) { details ->
                    TransactionItemRow(
                        details = details,
                        onClick = { onTransactionClick(details.transaction.id) },
                        onDelete = { onDelete(details.transaction.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionItemRow(
    details: TransactionDetails,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val isExpense = details.transaction.type == "expense"
    val amountColor = if (isExpense) CBMoneyColors.Red else CBMoneyColors.Green
    val prefix = if (isExpense) "- " else "+ "

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Category icon placeholder
        Text(
            text = details.categoryName ?: stringResource(R.string.unknown),
            style = CBMoneyTypography.Body.Medium.Medium,
            modifier = Modifier.weight(1.5f)
        )
        Text(
            text = "$prefix${details.transaction.amount.formatMoney()}",
            style = CBMoneyTypography.Body.Medium.Medium,
            color = amountColor,
            modifier = Modifier.weight(1.5f)
        )
        Text(
            text = details.transaction.description.ifEmpty { "-" },
            style = CBMoneyTypography.Body.Medium.Regular,
            color = CBMoneyColors.Text.TextTertiary,
            modifier = Modifier.weight(2f)
        )
        val dateFormatted = remember(details.transaction.date) {
        Instant.ofEpochMilli(details.transaction.date)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }
    Text(
            text = dateFormatted,
            style = CBMoneyTypography.Body.Small.Regular,
            color = CBMoneyColors.Text.TextTertiary,
            modifier = Modifier.weight(1.5f)
        )
        IconButton(
            onClick = onDelete,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.delete),
                tint = CBMoneyColors.Red,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
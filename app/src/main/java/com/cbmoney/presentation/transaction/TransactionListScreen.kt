package com.cbmoney.presentation.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.cbmoney.utils.exts.hexToColor
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
        onSearch = { query ->
            viewModel.processIntent(TransactionListIntent.SearchQueryChanged(query))
        },
        onCategorySelected = { category ->
            viewModel.processIntent(TransactionListIntent.SelectCategory(category))
        },
        onTransactionClick = onTransactionClick,
        onDelete = { transactionId ->
            viewModel.processIntent(TransactionListIntent.DeleteTransaction(transactionId))
        }
    )
}

@Composable
fun TransactionListScreenContent(
    state: TransactionListState,
    onBack: () -> Unit,
    onSearch: (String) -> Unit,
    onCategorySelected: (Category?) -> Unit,
    onTransactionClick: (String) -> Unit,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.md)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { onBack() }
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
            )
        }

        // Category filter row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.md)
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
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                items(state.transactions) { details ->
                    TransactionItemRow(
                        details = details,
                        onTransactionClick = { onTransactionClick(details.transaction.id) },
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
    onTransactionClick: () -> Unit,
    onDelete: () -> Unit
) {
    val isExpense = details.transaction.type == "expense"
    val amountColor = if (isExpense) CBMoneyColors.Red else CBMoneyColors.Green
    val prefix = if (isExpense) "- " else "+ "

    val dateFormatted = remember(details.transaction.date) {
        Instant.ofEpochMilli(details.transaction.date)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTransactionClick() }
            .padding(vertical = Spacing.sm, horizontal = Spacing.sm)
            .background(
                color = CBMoneyColors.BackGround.BackgroundSecondary,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        // Category icon with background
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    color = try {
                        details.iconColor?.hexToColor() ?: CBMoneyColors.Primary.Primary
                    } catch (e: Exception) {
                        CBMoneyColors.Primary.Primary
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = details.categoryIcon?.take(1) ?: "📦",
                style = CBMoneyTypography.Body.Large.Bold
            )
        }

        // Category name and description
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = details.categoryName ?: stringResource(R.string.unknown),
                style = CBMoneyTypography.Body.Medium.Bold,
                color = CBMoneyColors.Text.TextPrimary
            )
            Text(
                text = details.transaction.description.ifEmpty { "-" },
                style = CBMoneyTypography.Body.Small.Regular,
                color = CBMoneyColors.Text.TextTertiary,
                maxLines = 1
            )
        }

        // Amount and date (right side)
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "$prefix${details.transaction.amount.formatMoney()}",
                style = CBMoneyTypography.Body.Medium.Bold,
                color = amountColor
            )
            Text(
                text = dateFormatted,
                style = CBMoneyTypography.Body.Small.Regular,
                color = CBMoneyColors.Text.TextTertiary
            )
        }
    }
}

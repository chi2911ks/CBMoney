package com.cbmoney.presentation.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.R
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.presentation.transaction.contract.TransactionDetailsIntent
import com.cbmoney.presentation.transaction.contract.TransactionDetailsState
import com.cbmoney.presentation.transaction.viewmodel.TransactionDetailsViewModel
import com.cbmoney.utils.exts.formatMoney
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun TransactionDetailsScreen(
    transactionId: String,
    onBack: () -> Unit = {}
) {
    val viewModel: TransactionDetailsViewModel = koinViewModel()
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(transactionId) {
        viewModel.processIntent(TransactionDetailsIntent.LoadTransaction(transactionId))
    }

    TransactionDetailsScreenContent(
        state = uiState,
        onBack = onBack,
        onDelete = { viewModel.processIntent(TransactionDetailsIntent.DeleteTransaction) }
    )
}

@Composable
fun TransactionDetailsScreenContent(
    state: TransactionDetailsState,
    onBack: () -> Unit,
    onDelete: () -> Unit
) {
    val transaction = state.transaction?.transaction
    val categoryName = state.transaction?.categoryName
    val categoryIcon = state.transaction?.categoryIcon
    val iconColor = state.transaction?.iconColor

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        // Top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.md)
                .padding(top = Spacing.md, bottom = Spacing.md)
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
                text = stringResource(R.string.transaction_details),
                style = CBMoneyTypography.Title.Large.Bold,
                modifier = Modifier.align(Alignment.Center)
            )

            IconButton(
                onClick = onDelete,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete),
                    tint = CBMoneyColors.Red
                )
            }
        }

        // Content
        if (transaction == null) {
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
            val isExpense = transaction.type == "expense"
            val amountColor = if (isExpense) CBMoneyColors.Red else CBMoneyColors.Green
            val prefix = if (isExpense) "- " else "+ "

            val backgroundColor = try {
                iconColor?.let { Color(it.toColorInt()) } ?: CBMoneyColors.Gray.Gray
            } catch (e: Exception) {
                CBMoneyColors.Gray.Gray
            }

            // Main content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.md),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Category icon
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = categoryIcon ?: "?",
                        fontSize = 48.sp,
                        color = CBMoneyColors.White
                    )
                }

                // Category name
                Text(
                    text = categoryName ?: stringResource(R.string.unknown),
                    style = CBMoneyTypography.Title.Large.Medium,
                    modifier = Modifier.padding(top = Spacing.lg)
                )

                // Amount
                Text(
                    text = "$prefix${transaction.amount.formatMoney()}",
                    style = CBMoneyTypography.Headline.Large.Bold.copy(
                        fontSize = 44.sp
                    ),
                    color = amountColor,
                    modifier = Modifier.padding(top = Spacing.md)
                )

                // Date
                val dateFormatted = Instant.ofEpochMilli(transaction.date)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))

                Text(
                    text = dateFormatted,
                    style = CBMoneyTypography.Body.Large.Regular,
                    color = CBMoneyColors.Text.TextTertiary,
                    modifier = Modifier.padding(top = Spacing.sm)
                )

                // Divider
                Divider(
                    color = CBMoneyColors.Border.BorderLight,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = Spacing.lg)
                )

                // Details section
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Description
                    if (transaction.description.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.note),
                            style = CBMoneyTypography.Body.Large.Bold,
                            modifier = Modifier.padding(bottom = Spacing.sm)
                        )
                        Text(
                            text = transaction.description,
                            style = CBMoneyTypography.Body.Large.Regular,
                            color = CBMoneyColors.Text.TextSecondary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = Spacing.lg)
                        )
                    }

                    // Category info
                    Text(
                        text = stringResource(R.string.categories),
                        style = CBMoneyTypography.Body.Large.Bold,
                        modifier = Modifier.padding(bottom = Spacing.sm)
                    )
                    Text(
                        text = categoryName ?: stringResource(R.string.unknown),
                        style = CBMoneyTypography.Body.Large.Regular,
                        color = CBMoneyColors.Text.TextSecondary,
                        modifier = Modifier.padding(bottom = Spacing.lg)
                    )

                    // Transaction type
                    Text(
                        text = stringResource(R.string.transaction),
                        style = CBMoneyTypography.Body.Large.Bold,
                        modifier = Modifier.padding(bottom = Spacing.sm)
                    )
                    Text(
                        text = if (isExpense) stringResource(R.string.expense) else stringResource(R.string.income),
                        style = CBMoneyTypography.Body.Large.Regular,
                        color = amountColor,
                        modifier = Modifier.padding(bottom = Spacing.lg)
                    )
                }
            }
        }
    }
}
package com.cbmoney.presentation.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.cbmoney.domain.model.CategoryType
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

    // Load transaction data
    androidx.compose.runtime.LaunchedEffect(transactionId) {
        viewModel.loadTransactionById(transactionId)
    }

    TransactionDetailsScreenContent(
        state = uiState,
        onBack = onBack,
        onDelete = { viewModel.processIntent(TransactionDetailsIntent.DeleteTransaction) },
        onEdit = { /* TODO: navigate to edit */ }
    )
}

@Composable
fun TransactionDetailsScreenContent(
    state: TransactionDetailsState,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    val transaction = state.transactionDetails?.transaction
    val categoryName = state.transactionDetails?.categoryName
    val categoryIcon = state.transactionDetails?.categoryIcon
    val iconColor = state.transactionDetails?.iconColor

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
                text = stringResource(R.string.transaction_details),
                style = CBMoneyTypography.Body.Large.Bold,
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
            // Category Icon
            val isExpense = transaction.type == "expense"
            val amountColor = if (isExpense) CBMoneyColors.Red else CBMoneyColors.Green
            val prefix = if (isExpense) "- " else "+ "

            val backgroundColor = try {
                iconColor?.let { Color(it.toColorInt()) } ?: CBMoneyColors.Gray.Gray
            } catch (e: Exception) {
                CBMoneyColors.Gray.Gray
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Spacing.xl),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Category icon
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = categoryIcon ?: "?",
                        fontSize = 32.sp
                    )
                }

                // Category name
                Text(
                    text = categoryName ?: stringResource(R.string.unknown),
                    style = CBMoneyTypography.Title.Large.Medium,
                    modifier = Modifier.padding(top = Spacing.md)
                )

                // Amount
                Text(
                    text = "$prefix${transaction.amount.formatMoney()}",
                    style = CBMoneyTypography.Headline.Large.Bold.copy(
                        fontSize = 40.sp
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
                    style = CBMoneyTypography.Body.Medium.Regular,
                    color = CBMoneyColors.Text.TextTertiary,
                    modifier = Modifier.padding(top = Spacing.sm)
                )

                // Description
                if (transaction.description.isNotEmpty()) {
                    Text(
                        text = transaction.description,
                        style = CBMoneyTypography.Body.Large.Regular,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = Spacing.lg)
                    )
                }
            }
        }
    }
}
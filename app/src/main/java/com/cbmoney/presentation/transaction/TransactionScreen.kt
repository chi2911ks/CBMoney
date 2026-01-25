package com.cbmoney.presentation.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cbmoney.R
import com.cbmoney.domain.constants.DefaultCategoriesMap
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.presentation.components.ButtonPrimary
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.presentation.transaction.components.AmountInput
import com.cbmoney.presentation.transaction.components.CategoryItem
import com.cbmoney.presentation.transaction.components.DatePickerField
import com.cbmoney.presentation.transaction.components.NoteTextField
import com.cbmoney.presentation.transaction.components.TabTransaction
import com.cbmoney.utils.exts.formatMoney
import com.cbmoney.utils.exts.toFormatDate


@Composable
fun TransactionScreen(
    onBackNavigation: () -> Unit
) {
    TransactionScreenContent(
        onBackNavigation = onBackNavigation,
        navigateToCategory = {}
    )
}

@Composable
fun TransactionScreenContent(
    onBackNavigation: () -> Unit,
    navigateToCategory: (CategoryType) -> Unit
) {
    var currentTab by remember { mutableStateOf(CategoryType.EXPENSE) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .statusBarsPadding()
            .padding(horizontal = Spacing.md)
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        onBackNavigation()
                    }
            )

            Text(
                text = stringResource(R.string.add_transaction),
                style = CBMoneyTypography.Body.Large.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(Spacing.xl))
        TabTransaction(
            tabSelected = currentTab,
            onTabSelected = { currentTab = it }
        )
        Spacer(modifier = Modifier.height(Spacing.md))
        val datePickerState =
            rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var amount by remember { mutableStateOf("0") }
            Text(
                text = stringResource(R.string.amount),
                style = CBMoneyTypography.Body.Large.Bold
            )
            AmountInput(
                value = amount.formatMoney(),
                onValueChange = { input ->
                    val clean = input
                        .replace(".", "")
                    if (clean.all { it.isDigit() }) {
                        amount = clean
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = CBMoneyTypography.Headline.Large.Medium.copy(
                    fontSize = 40.sp
                )
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
            CategoriesContent(
                currentTab,
                navigateToCategory
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
            DateInputDialog(datePickerState)
            Spacer(modifier = Modifier.height(Spacing.sm))
            NoteTextField(
                label = stringResource(R.string.note),
                value = "",
                placeholder = stringResource(R.string.add_trans_des),
                onValueChange = {},

                )
        }
        Spacer(modifier = Modifier.height(Spacing.md))
        ButtonPrimary(
            text = stringResource(R.string.save_transaction),
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
        )
    }
}


@Composable
fun DateInputDialog(
    datePickerState: DatePickerState,
) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Text(
                    text = stringResource(R.string.select),
                    color = CBMoneyColors.Primary.Primary,
                    style = CBMoneyTypography.Body.Large.Bold,
                    modifier = Modifier
                        .padding(Spacing.sm)
                        .clickable { showDialog = false }
                )
            },
            dismissButton = {
                Text(
                    text = stringResource(R.string.cancel),
                    color = CBMoneyColors.Black,
                    style = CBMoneyTypography.Body.Large.Bold,
                    modifier = Modifier
                        .padding(Spacing.sm)
                        .clickable { showDialog = false }
                )
            },
            colors = DatePickerDefaults.colors(
                containerColor = CBMoneyColors.BackGround.BackgroundPrimary,
            )
        ){
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = CBMoneyColors.BackGround.BackgroundPrimary,

                    // ngày được chọn
                    selectedDayContainerColor = CBMoneyColors.Primary.Primary.copy(0.3f),
                    selectedDayContentColor = CBMoneyColors.Text.TextPrimary,

                    // ngày hôm nay (vòng tròn)
                    todayDateBorderColor = CBMoneyColors.BackGround.BackgroundPrimary,
                    todayContentColor = CBMoneyColors.Text.TextPrimary,

                    // ngày bình thường
                    dayContentColor = CBMoneyColors.Text.TextPrimary,
                    weekdayContentColor = CBMoneyColors.Text.TextSecondary
                )
            )

        }
    }
    val selectedMillis = datePickerState.selectedDateMillis
    selectedMillis?.let{
        DatePickerField(
            label = stringResource(R.string.date).uppercase(),
            value = selectedMillis.toFormatDate(),
            onClick = { showDialog = true },
        )
    }




}
@Composable
fun CategoriesContent(
    type: CategoryType = CategoryType.EXPENSE,
    navigateToCategory: (CategoryType) -> Unit
) {
    val default = DefaultCategoriesMap.categories[type]
    var selected by remember { mutableStateOf(default?.get(0)["name"]) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = stringResource(R.string.categories),
                style = CBMoneyTypography.Body.Large.Bold,
            )
            Text(
                text = stringResource(R.string.see_all),
                style = CBMoneyTypography.Body.Medium.Medium,
                textDecoration = TextDecoration.Underline,
                color = CBMoneyColors.Primary.Primary,
                modifier = Modifier
                    .clickable {
                        navigateToCategory(type)
                    }
            )
        }

        Spacer(modifier = Modifier.height(Spacing.sm))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .height(250.dp),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            DefaultCategoriesMap.categories[type]?.let {
                items(it.size) { index ->
                    val item = it[index]
                    CategoryItem(
                        selected == item["name"] as String,
                        { name-> selected = name },
                        item["icon"] as String,
                        item["name"] as String,
                        item["color"] as String,
                    )
                }
            }

        }

    }
}

@Preview
@Composable
private fun TransactionScreenContentPreview() {
    TransactionScreenContent({}, {})



}

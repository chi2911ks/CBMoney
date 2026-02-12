package com.cbmoney.presentation.transaction

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.R
import com.cbmoney.domain.model.Category
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.presentation.app.SnackbarManager
import com.cbmoney.presentation.app.UiMessage
import com.cbmoney.presentation.components.button.ButtonPrimary
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.presentation.transaction.components.AmountInput
import com.cbmoney.presentation.transaction.components.CategoryItem
import com.cbmoney.presentation.transaction.components.DateInputDialog
import com.cbmoney.presentation.transaction.components.NoteTextField
import com.cbmoney.presentation.transaction.components.TabTransaction
import com.cbmoney.presentation.transaction.contract.AddTransactionEvent
import com.cbmoney.presentation.transaction.contract.AddTransactionIntent
import com.cbmoney.presentation.transaction.contract.AddTransactionState
import com.cbmoney.presentation.transaction.viewmodel.AddTransactionViewModel
import com.cbmoney.utils.exts.formatDigit
import com.cbmoney.utils.exts.formatMoney
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTransactionScreen(
    currentType: CategoryType = CategoryType.EXPENSE,
    onBackNavigation: () -> Unit,
    navigateToCategory: (CategoryType) -> Unit,
    navigateToTransactionList: () -> Unit,
    viewModel: AddTransactionViewModel = koinViewModel(),
    snackbarManager: SnackbarManager = koinInject()
) {
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.processIntent(AddTransactionIntent.ChangeTab(currentType))
    }
    LaunchedEffect(Unit) {
        viewModel.singleEvent.collectLatest {
            when(it){
                AddTransactionEvent.SaveTransactionSuccess -> {
                    snackbarManager.show(
                        UiMessage.Res(R.string.save_success)
                    )
                }
                is AddTransactionEvent.SaveTransactionError -> {
                    snackbarManager.show(
                        UiMessage.Text(it.message)
                    )
                }
            }
        }

    }
    TransactionScreenContent(
        uiState,
        onBackNavigation = onBackNavigation,
        navigateToCategory = navigateToCategory,
        navigateToTransactionList = navigateToTransactionList,
        processIntent = viewModel::processIntent
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionScreenContent(
    uiState: AddTransactionState,
    onBackNavigation: () -> Unit,
    navigateToCategory: (CategoryType) -> Unit,
    navigateToTransactionList: () -> Unit,
    processIntent: (AddTransactionIntent) -> Unit = {},
) {
//    var currentTab by remember { mutableStateOf(CategoryType.EXPENSE) }
    val categories = uiState.categories.filter { it.type == uiState.selectedType }
    val datePickerState =
        rememberDatePickerState()
//    var selectedId by remember { mutableStateOf("") }
    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            processIntent(AddTransactionIntent.ChangeDate(it))
        }
    }
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
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        navigateToTransactionList()
                    }
            )
        }
        Spacer(modifier = Modifier.height(Spacing.xl))
        TabTransaction(
            tabSelected = uiState.selectedType,
            onTabSelected = {
                processIntent(AddTransactionIntent.ChangeTab(it))
            }
        )
        Spacer(modifier = Modifier.height(Spacing.md))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.amount),
                style = CBMoneyTypography.Body.Large.Bold
            )
            AmountInput(
                value = uiState.amount.formatMoney(),
                onValueChange = {
                    val input = it.formatDigit()
                    processIntent(AddTransactionIntent.ChangeAmount(input?:0))

                },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = CBMoneyTypography.Headline.Large.Medium.copy(
                    fontSize = 40.sp
                )
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
            CategoriesContent(
                categories,
                uiState.selectedCategory,
                {
                    processIntent(AddTransactionIntent.SelectCategory(it))
                },
                {
                    navigateToCategory(uiState.selectedType)
                }
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
            DateInputDialog(datePickerState)
            Spacer(modifier = Modifier.height(Spacing.sm))
            NoteTextField(
                label = stringResource(R.string.note),
                value = uiState.note,
                placeholder = stringResource(R.string.add_trans_des),
                onValueChange = {
                    processIntent(AddTransactionIntent.ChangeNote(it))
                }
            )
        }
        Spacer(modifier = Modifier.height(Spacing.md))
        ButtonPrimary(
            text = stringResource(R.string.save_transaction),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                processIntent(AddTransactionIntent.SaveTransaction)
            },
        )
    }
}


@Composable
fun CategoriesContent(
    categories: List<Category>,
    selectedCategory: Category?,
    onSelectedChange: (Category) -> Unit,
    navigateToCategory: () -> Unit
) {
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
        ) {
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
                        navigateToCategory()
                    }
            )
        }

        Spacer(modifier = Modifier.height(Spacing.sm))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .heightIn(max = 250.dp),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            items(
                items = categories,
                key = { it.id }
            ) { cate ->
                CategoryItem(
                    selectedCategory == cate,
                    { onSelectedChange(cate) },
                    category = cate
                )
            }
        }

    }
}

@Preview
@Composable
private fun TransactionScreenContentPreview() {


}

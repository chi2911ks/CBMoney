package com.cbmoney.presentation.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.R
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.presentation.category.components.ColorPicker
import com.cbmoney.presentation.category.components.IconPicker
import com.cbmoney.presentation.category.components.PreviewCategoryCard
import com.cbmoney.presentation.category.components.RadioButtonText
import com.cbmoney.presentation.category.contract.AddCategoryEvent
import com.cbmoney.presentation.category.contract.AddCategoryIntent
import com.cbmoney.presentation.category.contract.AddCategoryState
import com.cbmoney.presentation.category.viewmodel.AddCategoryViewModel
import com.cbmoney.presentation.components.ButtonPrimary
import com.cbmoney.presentation.components.IconResolver
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.hexToColor
import com.cbmoney.utils.exts.rawClickable
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddCategoryScreen(
    currentType: CategoryType,
    onBackNavigation: () -> Unit,
    viewModel: AddCategoryViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit){
        viewModel.processIntent(AddCategoryIntent.OnTypeChanged(currentType))
        viewModel.singleEvent.collect{
            when(it){
                is AddCategoryEvent.NavigateToCategories -> onBackNavigation()
                is AddCategoryEvent.ShowError -> snackBarHostState.showSnackbar(it.message)
            }
        }
    }
    AddCategoryScreenContent(
        uiState,
        onBackNavigation = onBackNavigation,
        snackBarHostState = snackBarHostState,
        processIntent = viewModel::processIntent
    )
}



@Composable
fun AddCategoryScreenContent(
    uiState: AddCategoryState,
    onBackNavigation: () -> Unit,
    snackBarHostState: SnackbarHostState,
    processIntent: (AddCategoryIntent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CBMoneyColors.BackGround.BackgroundPrimary)
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CBMoneyColors.White)
                    .statusBarsPadding()
                    .padding(horizontal = Spacing.md)
                    .padding(bottom = Spacing.md)
            ){
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
                    text = stringResource(R.string.add_category),
                    style = CBMoneyTypography.Body.Large.Bold,
                    modifier = Modifier.align(Alignment.Center)

                )

                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = CBMoneyColors.Green2,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .rawClickable{
                            processIntent(AddCategoryIntent.SaveCategory)
                        }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(horizontal = Spacing.md),

                ) {
                Spacer(modifier = Modifier.height(Spacing.xl))
                PreviewCategoryCard(
                    name = uiState.name,
                    color = uiState.color.hexToColor(),
                    icon = IconResolver.getImageVector(uiState.icon),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .selectableGroup()

                ){
                    RadioButtonText(
                        text = stringResource(R.string.expense),
                        selected = uiState.type == CategoryType.EXPENSE,
                        onOptionSelected = {
                            processIntent(AddCategoryIntent.OnTypeChanged(CategoryType.EXPENSE))
                        }
                    )
                    Spacer(modifier = Modifier.width(Spacing.sm))
                    RadioButtonText(
                        text = stringResource(R.string.income),
                        selected = uiState.type == CategoryType.INCOME,
                        onOptionSelected = {
                            processIntent(AddCategoryIntent.OnTypeChanged(CategoryType.INCOME))
                        }
                    )


                }
                Text(
                    stringResource(R.string.name_category),
                    style = CBMoneyTypography.Body.Large.Bold
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = {
                        processIntent(AddCategoryIntent.OnNameChanged(it))
                    },
                    trailingIcon = {
                        if (uiState.name.isNotEmpty()) {
                            IconButton(onClick = {
                                processIntent(AddCategoryIntent.OnNameChanged(""))
                            }) {
                                Icon(Icons.Default.Clear, null)
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CBMoneyColors.Transparent,
                        unfocusedBorderColor = CBMoneyColors.Transparent,
                        focusedTextColor = CBMoneyColors.Text.TextPrimary,
                        unfocusedTextColor = CBMoneyColors.Text.TextPrimary,
                        cursorColor = CBMoneyColors.Text.TextPrimary,
                        focusedContainerColor = CBMoneyColors.White,
                        unfocusedContainerColor = CBMoneyColors.White
                    ),
                    shape = CBMoneyShapes.large,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            stringResource(R.string.example_category),
                            style = CBMoneyTypography.Body.Medium.Regular
                        )
                    },
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
                IconPicker(
                    nameIcon = uiState.icon,
                    onIconSelected = {
                        processIntent(AddCategoryIntent.OnIconSelected(it))
                    }
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
                ColorPicker(
                    uiState.color,
                    onColorChanged = {
                        processIntent(AddCategoryIntent.OnColorSelected(it))
                    }
                )

            }
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(CBMoneyColors.White)
                    .padding(Spacing.md)
            ){
                ButtonPrimary(
                    text = stringResource(R.string.save),
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = null
                        )
                    },
                    onClick = {
                        processIntent(
                            AddCategoryIntent.SaveCategory
                        )
                    },
                )
            }
        }
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = Spacing.lg)
        )
    }

}



@Preview
@Composable
private fun AddCategoryScreenContentPreview() {
    AddCategoryScreenContent(
        uiState = AddCategoryState(),
        onBackNavigation = {},
        snackBarHostState = SnackbarHostState(),
        processIntent = {}

    )
}
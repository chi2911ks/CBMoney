package com.cbmoney.presentation.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.ShoppingBasket
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
import com.cbmoney.domain.model.Category
import com.cbmoney.presentation.category.components.ColorPicker
import com.cbmoney.presentation.category.components.IconPicker
import com.cbmoney.presentation.category.components.PreviewCategoryCard
import com.cbmoney.presentation.category.contract.EditCategoryEvent
import com.cbmoney.presentation.category.contract.EditCategoryIntent
import com.cbmoney.presentation.category.contract.EditCategoryState
import com.cbmoney.presentation.category.viewmodel.EditCategoryViewModel
import com.cbmoney.presentation.components.button.ButtonPrimary
import com.cbmoney.presentation.common.CategoryIconResolver
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.hexToColor
import com.cbmoney.utils.exts.rawClickable
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditCategoryScreen(
    category: Category,
    onBackNavigation: () -> Unit,
    viewModel: EditCategoryViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit){
        viewModel.processIntent(EditCategoryIntent.LoadCategoryDefault(category))
        viewModel.singleEvent.collect{
            when(it){
                is EditCategoryEvent.NavigateToBack -> onBackNavigation()
                is EditCategoryEvent.ShowError -> snackBarHostState.showSnackbar(it.message)
            }
        }
    }

    EditCategoryScreenContent(
        uiState,
        onBackNavigation = onBackNavigation,
        snackBarHostState = snackBarHostState,
        processIntent = viewModel::processIntent
    )
}

@Composable
fun EditCategoryScreenContent(
    uiState: EditCategoryState,
    onBackNavigation: () -> Unit,
    snackBarHostState: SnackbarHostState,
    processIntent: (EditCategoryIntent) -> Unit
) {

//    ConfirmDialog(
//        title = "Xoá danh mục",
//        message = "Bạn có chắc chắn muốn xoá?",
//        onConfirm = {
//
//        },
//        onDismiss = {},
//        onCancel = {}
//    )
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
                    text = stringResource(R.string.edit_category),
                    style = CBMoneyTypography.Body.Large.Bold,
                    modifier = Modifier.align(Alignment.Center)

                )
                if (!uiState.category.isDefault){
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = CBMoneyColors.Red2,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .rawClickable{
                                processIntent(EditCategoryIntent.DeleteCategory)
                            }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(horizontal = Spacing.md),

                ) {
                Spacer(modifier = Modifier.height(Spacing.xl))
                PreviewCategoryCard(
                    name = uiState.category.name,
                    color = uiState.category.iconColor.hexToColor(),
                    icon = CategoryIconResolver.CategoryIconMap[uiState.category.icon] ?: Icons.Default.ShoppingBasket,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                if (!uiState.category.isDefault){
                    Text(
                        stringResource(R.string.name_category),
                        style = CBMoneyTypography.Body.Large.Bold
                    )
                    Spacer(modifier = Modifier.height(Spacing.sm))

                    OutlinedTextField(
                        value = uiState.category.name,
                        onValueChange = {
                            processIntent(EditCategoryIntent.OnNameChanged(it))
                        },
                        trailingIcon = {
                            if (uiState.category.name.isNotEmpty()) {
                                IconButton(onClick = {
                                    processIntent(EditCategoryIntent.OnNameChanged(""))
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
                        nameIcon = uiState.category.icon,
                        onIconSelected = {
                            processIntent(EditCategoryIntent.OnIconSelected(it))
                        }
                    )
                }

                Spacer(modifier = Modifier.height(Spacing.sm))
                ColorPicker(
                    uiState.category.iconColor,
                    onColorChanged = {
                        processIntent(EditCategoryIntent.OnColorSelected(it))
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
                        processIntent(EditCategoryIntent.SaveCategory)
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
private fun EditCategoryScreenContentPreview() {

}
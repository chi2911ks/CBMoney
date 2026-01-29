package com.cbmoney.presentation.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.R
import com.cbmoney.domain.model.Category
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.presentation.category.components.CategoryCard
import com.cbmoney.presentation.category.components.TabCategory
import com.cbmoney.presentation.category.contract.CategoriesIntent
import com.cbmoney.presentation.category.contract.CategoriesState
import com.cbmoney.presentation.category.viewmodel.CategoriesViewModel
import com.cbmoney.presentation.components.ButtonPrimary
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyColors.Neutral.NeutralGray
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.rawClickable
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    categoryCurrent: CategoryType = CategoryType.EXPENSE,
    onBackNavigation: () -> Unit,
    navigateToAddCategory: (CategoryType) -> Unit,
    navigateToEditCategory: (Category) -> Unit,
    viewModel: CategoriesViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()

    CategoriesContent(
        uiState,
        categoryCurrent,
        onBackNavigation = onBackNavigation,
        navigateToAddCategory = navigateToAddCategory,
        navigateToEditCategory = navigateToEditCategory,
        processIntent = viewModel::processIntent
    )
}

@Composable
fun CategoriesContent(
    uiState: CategoriesState,
    categoryCurrent: CategoryType,
    onBackNavigation: () -> Unit,
    navigateToAddCategory: (CategoryType) -> Unit,
    navigateToEditCategory: (Category) -> Unit,
    processIntent: (CategoriesIntent) -> Unit = {}
) {
    var currentTab by remember { mutableStateOf(categoryCurrent) }
    val categories = uiState.categories.filter { it.type == currentTab }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .statusBarsPadding()
            .padding(horizontal = Spacing.md)
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()

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
                    text = stringResource(R.string.category_management),
                    style = CBMoneyTypography.Body.Large.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(Spacing.xl))
            TabCategory(
                selected = currentTab,
                onSelectedChange = {
                    currentTab = it
                }
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
            Text(
                text = stringResource(R.string.category_list).uppercase(),
                color = NeutralGray,
                style = CBMoneyTypography.Title.Small.Medium
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
            LazyColumn(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = categories,
                    key = {it.id}
                ){category->
                    CategoryCard(
                        category = category,
                        onDelete = {
                            processIntent(CategoriesIntent.DeleteCategory(category.id))
                        },
                        onEdit = {
                            navigateToEditCategory(it)
                        }
                    )
                }
            }
        }
        ButtonPrimary(
            text = stringResource(R.string.add_category),
            onClick = {
                navigateToAddCategory(currentTab)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd).padding(bottom = Spacing.lg)
        )
    }

}
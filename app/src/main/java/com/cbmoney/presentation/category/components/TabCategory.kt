package com.cbmoney.presentation.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.R
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.rawClickable

@Composable
fun TabCategory(
    modifier: Modifier = Modifier,
    selected: CategoryType = CategoryType.EXPENSE,
    onSelectedChange: (CategoryType) -> Unit,
) {

    Row(
        modifier = modifier
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
    ){
        TabItem(
            text = stringResource(R.string.expense),
            selected = selected == CategoryType.EXPENSE,
            onSelectedChange = {
                onSelectedChange(CategoryType.EXPENSE)
            },
            modifier = Modifier.weight(1f)
        )
        TabItem(
            text = stringResource(R.string.income),
            selected = selected == CategoryType.INCOME,
            onSelectedChange = {
                onSelectedChange(CategoryType.INCOME)
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun TabItem(
    text: String,
    selected: Boolean,
    onSelectedChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .rawClickable { onSelectedChange() },
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = text,
            style = CBMoneyTypography.Body.Large.Medium,
            modifier = Modifier.padding(Spacing.sm)
        )
        HorizontalDivider(
            thickness = 2.dp,
            color = if (selected) CBMoneyColors.Primary.Primary else CBMoneyColors.Gray.Gray
        )
    }
}

@Preview
@Composable
private fun TabCategoryPreview() {
    TabCategory(
        selected = CategoryType.EXPENSE,
        onSelectedChange = {}
    )
}
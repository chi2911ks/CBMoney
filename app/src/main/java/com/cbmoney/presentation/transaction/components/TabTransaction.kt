package com.cbmoney.presentation.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.R
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.rawClickable

@Composable
fun TabTransaction(
    modifier: Modifier = Modifier,
    tabSelected: CategoryType = CategoryType.EXPENSE,
    onTabSelected: (CategoryType) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(CBMoneyShapes.extraLarge)
            .border(
                1.dp,
                CBMoneyColors.Border.BorderLight,
                CBMoneyShapes.extraLarge
            )
            .background(CBMoneyColors.Gray.OnGray.copy(0.1f))
            .padding(Spacing.xs),
    ) {
        TabItem(
            selected = tabSelected == CategoryType.EXPENSE,
            onSelected = {
                onTabSelected(CategoryType.EXPENSE)
            },
            label = stringResource(R.string.expense),
            type = CategoryType.EXPENSE,
            modifier = Modifier.weight(1f)
        )
        TabItem(
            selected = tabSelected == CategoryType.INCOME,
            onSelected = {
                onTabSelected(CategoryType.INCOME)
            },
            label = stringResource(R.string.income),
            type = CategoryType.INCOME,
            modifier = Modifier.weight(1f)
        )
    }


}

@Composable
fun TabItem(
    selected: Boolean,
    onSelected: (CategoryType) -> Unit,
    label: String,
    type: CategoryType,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .heightIn(36.dp)
            .clip(CBMoneyShapes.extraLarge)
            .background(
                if (selected) CBMoneyColors.Primary.Primary.copy(0.2f)
                else CBMoneyColors.Transparent)
            .rawClickable {
                onSelected(type)
            }
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = CBMoneyTypography.Title.Small.Medium
        )
    }
}
@Preview
@Composable
fun TabTransactionPreview(modifier: Modifier = Modifier) {
    TabTransaction(
        modifier = modifier,
        onTabSelected = {}
    )
}
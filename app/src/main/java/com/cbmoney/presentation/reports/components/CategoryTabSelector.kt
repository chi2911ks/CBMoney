package com.cbmoney.presentation.reports.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cbmoney.R
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.rawClickable

@Composable
fun CategoryTabSelector(
    modifier: Modifier = Modifier,
    selected: CategoryType = CategoryType.EXPENSE,
    onSelectedChange: (CategoryType) -> Unit,
) {
    Row(
        modifier = modifier
            .background(CBMoneyColors.White)
    ){
        TabItem(
            text = stringResource(R.string.expense),
            selected = selected == CategoryType.EXPENSE,
            onSelectedChange = {
                onSelectedChange(CategoryType.EXPENSE)
            },
            modifier = Modifier.weight(1f),
            isLeft = true
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
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onSelectedChange: () -> Unit,
    isLeft: Boolean = false,
) {
    Column (
        modifier = modifier
            .rawClickable { onSelectedChange() },
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = text,
            style = CBMoneyTypography.Body.Large.Bold,
            modifier = Modifier.padding(Spacing.sm)
        )

        AnimatedVisibility(
            visible = selected,
            enter = fadeIn() + slideInHorizontally(
                animationSpec = tween(500),
                initialOffsetX = { fullWidth -> if (isLeft) fullWidth else -fullWidth }
            ),
        ) {
            HorizontalDivider(
                thickness = 4.dp,
                modifier = Modifier.clip(
                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ),
                color = CBMoneyColors.Primary.Primary
            )
        }

    }
}

package com.cbmoney.presentation.reports.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.cbmoney.R
import com.cbmoney.domain.model.CategorySpending
import com.cbmoney.presentation.common.CategoryIconResolver
import com.cbmoney.presentation.components.view.ProcessBar
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.formatMoney
import com.cbmoney.utils.exts.toHex
import java.math.RoundingMode

@Composable
fun CategorySpentItem(
    modifier: Modifier = Modifier,
    progress: Float,
    categorySpending: CategorySpending,
    onClick: () -> Unit
) {
    val color = Color( categorySpending.iconColor?.toColorInt() ?: Color.Gray.toHex().toColorInt())
    val interaction = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(CBMoneyShapes.small)
            .clickable(
                onClick = onClick,
                interactionSource = interaction,
                indication = ripple(),

            )
            .background(CBMoneyColors.White)
            .padding(vertical = Spacing.sm),
    ) {
        Box(
            modifier = Modifier
                .size(35.dp)
                .background(
                    color.copy(0.2f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = CategoryIconResolver.iconOf(categorySpending.categoryIcon?:""),
                contentDescription = null,
                tint = color

            )
        }
        Spacer(modifier = Modifier.width(Spacing.md))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = categorySpending.categoryName?:stringResource(R.string.unknown),
                        color = Color.Black,
                        style = CBMoneyTypography.Body.Medium.Bold
                    )
                    Text(
                        text = "${categorySpending.countTransaction} ${stringResource(R.string.transactions)}".lowercase(),
                        color = Color.Gray,
                        style = CBMoneyTypography.Body.Small.Regular
                    )
                }
                Column {
                    Text(
                        text = "- ${categorySpending.totalSpending.formatMoney()} đ",
                        color = CBMoneyColors.Red2,
                        style = CBMoneyTypography.Body.Small.Bold
                    )
                    Text(
                        text = (progress * 100).toBigDecimal()
                            .setScale(1, RoundingMode.DOWN)
                            .toString() + "%",
                        color = Color.Gray,
                        style = CBMoneyTypography.Body.Small.Regular,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
            Spacer(modifier = Modifier.height(Spacing.xs))
            ProcessBar(
                progress = progress,
                colorCategory = color,
                strokeWidth = 5.dp
            )
        }
    }
}

@Preview
@Composable
private fun CategorySpentItemPreview() {


}
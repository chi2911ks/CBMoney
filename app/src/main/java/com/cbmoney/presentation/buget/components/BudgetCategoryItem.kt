package com.cbmoney.presentation.buget.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.cbmoney.R
import com.cbmoney.presentation.common.IconResolver
import com.cbmoney.presentation.components.view.ProcessBar
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.formatMoney
import java.math.RoundingMode

@Composable
fun BudgetCategoryItem(
    modifier: Modifier = Modifier,
//    category: Category,
    name: String,
    iconColor: String,
    icon: String,
    budgetAmount: Long? = null,
    spentAmount: Long? = null,
    onClick: () -> Unit
) {
    val remaining = budgetAmount?.minus(spentAmount ?: 0)
    val (colorProcess, colorText) =
        remaining?.takeIf { it > 0 }?.let {
            CBMoneyColors.Primary.Primary to CBMoneyColors.Text.TextPrimary
        } ?: (
                CBMoneyColors.Red2 to CBMoneyColors.Red2
                )


    val colorCategory = Color(iconColor.toColorInt())
    val percentage = (spentAmount?.toFloat() ?: 0f) / (budgetAmount?.toFloat() ?: 1f)
    val safeProcess = percentage.takeIf { it.isFinite() } ?: 0f
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(CBMoneyShapes.large)
            .background(CBMoneyColors.White)
            .clickable{
                onClick()
            }
            .padding(Spacing.sm)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CBMoneyShapes.medium)
                    .background(colorCategory.copy(0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = IconResolver.getImageVector(icon),
                    contentDescription = null,
                    tint = colorCategory
                )
            }
            Spacer(modifier = Modifier.width(Spacing.sm))
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        style = CBMoneyTypography.Body.Medium.Bold
                    )
                    val annotatedText = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = CBMoneyColors.Text.TextPrimary,

                                fontWeight = CBMoneyTypography.Body.Small.Regular.fontWeight,
                            )
                        ) {
                            append(stringResource(R.string.remaining))
                        }
                        append(": ")
                        withStyle(
                            SpanStyle(
                                color = colorText,

                                fontWeight = CBMoneyTypography.Body.Medium.Bold.fontWeight,
                            )
                        ) {
                            append("${remaining?.formatMoney() ?: 0} đ")
                        }
                    }
                    Text(
                        text = annotatedText,
                    )
                }
                Spacer(modifier = Modifier.height(Spacing.xs))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (budgetAmount != null && spentAmount != null) {
                        val text =
                            "${stringResource(R.string.spent)}: ${spentAmount.formatMoney()} / ${budgetAmount.formatMoney()} đ"
                        Text(
                            text = text,
                            color = CBMoneyColors.Text.TextPrimary,
                            style = CBMoneyTypography.Body.Small.Regular
                        )
                    }
                    Text(
                        text = "${
                            (safeProcess * 100)
                                .toBigDecimal()
                                .setScale(1, RoundingMode.DOWN)
                                .toDouble()
                        }%",
                        color = CBMoneyColors.Text.TextPrimary,
                        style = CBMoneyTypography.Body.Small.Regular,
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(Spacing.sm))
        ProcessBar(progress = safeProcess, colorCategory = colorProcess)


    }
}

@Preview
@Composable
private fun BudgetCategoryItemPreview() {
//    BudgetCategoryItem(
//        category = DefaultCategories.INCOME_CATEGORIES[0],
//        budgetAmount = 2000000,
//        spentAmount = 1000000
//    )
}
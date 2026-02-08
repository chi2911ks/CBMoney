package com.cbmoney.presentation.buget.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.cbmoney.R
import com.cbmoney.presentation.common.IconResolver
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.formatDigit
import com.cbmoney.utils.exts.formatMoney

@Composable
fun ExpenditureCategoryItem(
    modifier: Modifier = Modifier,
//    category: Category,
    budget: String,
    iconColor: String,
    name: String,
    icon: String,
    onBudgetChange: (String) -> Unit,
) {
    val colorCategory = Color(iconColor.toColorInt())
    Row(
        modifier = modifier
            .clip(CBMoneyShapes.extraLarge)
            .background(CBMoneyColors.White)
            .padding(horizontal = Spacing.md, vertical = Spacing.sm),
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
        Text(
            text = name,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
            maxLines = 1,
            style = CBMoneyTypography.Body.Medium.Bold
        )
        Spacer(modifier = Modifier.width(Spacing.xs))
        BasicTextField(
            modifier = Modifier.weight(1f),
            value = budget.formatMoney(),
            onValueChange = {
                val input = it.formatDigit()
                onBudgetChange(input?.toString() ?: "")

            },
            textStyle = CBMoneyTypography.Body.Large.Bold.copy(
                color = CBMoneyColors.Primary.Primary,
                textAlign = TextAlign.End
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = {innerTextField->
                if (budget.isEmpty()){
                    Text(
                        text = stringResource(R.string.not_set),
                        style = CBMoneyTypography.Body.Large.Regular.copy(
                            color = CBMoneyColors.Neutral.NeutralGray,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.End
                        )
                    )
                }
                innerTextField()
            }
        )
        if (!budget.isEmpty()){
            Text(
                text = "đ",
                style = CBMoneyTypography.Body.Large.Bold.copy(
                    color = CBMoneyColors.Primary.Primary
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpenditureCategoryItemPreview() {
    var budget by remember {
        mutableStateOf("")
    }
//    ExpenditureCategoryItem(
//        category = DefaultCategories.EXPENSE_CATEGORIES[0],
//        budget = budget,
//        onBudgetChange = {
//            budget = it
//        }
//    )
}

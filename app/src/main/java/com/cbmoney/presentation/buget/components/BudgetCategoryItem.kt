package com.cbmoney.presentation.buget.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.cbmoney.domain.constants.DefaultCategories
import com.cbmoney.domain.model.Category
import com.cbmoney.presentation.components.IconResolver
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.formatMoney
import java.math.RoundingMode

@Composable
fun BudgetCategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    budgetAmount: Long? = null,
    spentAmount: Long? = null,
) {
    val (perTextColor, processColor) = when()
    val color = Color(category.iconColor.toColorInt())
    val percentage = (spentAmount?.toFloat() ?: 0f) / (budgetAmount?.toFloat() ?: 1f)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(CBMoneyShapes.large)
            .background(CBMoneyColors.White)
            .padding(Spacing.sm)
    ){
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CBMoneyShapes.medium)
                    .background(color.copy(0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = IconResolver.getImageVector(category.icon),
                    contentDescription = null,
                    tint = color
                )
            }
            Spacer(modifier = Modifier.width(Spacing.sm))
            Column (
                verticalArrangement = Arrangement.Center
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    Text(
                        text = category.name,
                        style = CBMoneyTypography.Body.Medium.Bold
                    )
                    Text(
                        text = "${(percentage*100).toBigDecimal().setScale(1, RoundingMode.DOWN).toDouble()}%",
                        style = CBMoneyTypography.Title.Small.Bold,
                    )
                }
                if (budgetAmount != null && spentAmount != null){
                    val text =
                        if (spentAmount > budgetAmount) "Vượt mức ${(spentAmount - budgetAmount).formatMoney()} đ"
                        else "Đã dùng ${spentAmount.formatMoney()} / ${budgetAmount.formatMoney()} đ"
                    Text(
                        text = text,
                        style = CBMoneyTypography.Body.Small.Regular
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(Spacing.sm))
        Box(
            modifier = Modifier

                .fillMaxWidth()
                .clip(CBMoneyShapes.extraLarge)
                .background(CBMoneyColors.Primary.Primary.copy(0.25f))
        ){

            Box(
                modifier = Modifier
                    .fillMaxWidth(percentage)
                    .height(10.dp)
                    .clip(CBMoneyShapes.extraLarge)
                    .background(CBMoneyColors.Primary.Primary)
            )
        }


    }
}

@Preview
@Composable
private fun BudgetCategoryItemPreview() {
    BudgetCategoryItem(
        category = DefaultCategories.INCOME_CATEGORIES[0],
        budgetAmount = 2000000,
        spentAmount =  3000000
    )
}
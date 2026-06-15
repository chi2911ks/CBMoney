package com.cbmoney.presentation.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.domain.model.Transaction
import com.cbmoney.presentation.common.CategoryIconResolver
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.formatMoney
import com.cbmoney.utils.exts.shadowCustom
import com.cbmoney.utils.fromPeriod

@Composable
fun TransactionItem(
    transaction: Transaction,
    categoryName: String?,
    categoryIcon: String?,
    iconColor: String?
) {
    if (categoryIcon.isNullOrBlank() || categoryName.isNullOrBlank() || iconColor.isNullOrBlank()) return
    val color = Color(iconColor.toColorInt())
    Row(
        modifier = Modifier
            .background(shape = CBMoneyShapes.large, color = CBMoneyColors.White)
            .padding(Spacing.sm)
            .shadowCustom(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color.copy(0.2f)),
            contentAlignment = Alignment.Center

        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = CategoryIconResolver.iconOf(categoryIcon),
                contentDescription = null,
                tint = color
            )
        }
        Spacer(Modifier.width(Spacing.sm))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = transaction.description.ifEmpty { categoryName },
                style = CBMoneyTypography.Body.Medium.Medium
            )
            Text(
                text = categoryName,
                style  = CBMoneyTypography.Body.Small.Regular,
                color = CBMoneyColors.Gray.Gray5)
        }
        val lol = transaction.type == fromPeriod(CategoryType.EXPENSE)
        val prefix =  if (lol) "-" else "+"
        Text(
            text = "$prefix${transaction.amount.formatMoney()} đ",
            color = if (lol) CBMoneyColors.Red2 else CBMoneyColors.Green2,
            style = CBMoneyTypography.Body.Medium.Bold.copy(
                textAlign = TextAlign.End
            ),
            modifier = Modifier
                .weight(1f)
                .padding(end = Spacing.sm)
        )
    }
}

@Preview
@Composable
private fun TransactionItemPrev() {

}
package com.cbmoney.presentation.home.components

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.cbmoney.R
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.domain.model.TransactionDetails
import com.cbmoney.presentation.common.CategoryIconResolver
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.DateUtils
import com.cbmoney.utils.exts.formatMoney
import com.cbmoney.utils.exts.toHex
import com.cbmoney.utils.fromPeriod

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecentTransactionItem(
    modifier: Modifier = Modifier,
    transactionDetails: TransactionDetails,
) {
    val colorCategory = Color( transactionDetails.iconColor?.toColorInt() ?: Color.Gray.toHex().toColorInt())
    Row(
        modifier = modifier
            .background(Color.White, CBMoneyShapes.extraLarge)
            .padding(Spacing.sm),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .background(colorCategory.copy(0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = CategoryIconResolver.iconOf(transactionDetails.categoryIcon?:""),
                contentDescription = null,
                tint = colorCategory,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(Spacing.sm))
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = transactionDetails.categoryName?: stringResource(R.string.unknown),
                color = Color.Black,
                style = CBMoneyTypography.Body.Medium.Bold
            )
            Text(
                text = DateUtils.formatTransactionDate(transactionDetails.transaction.date),
                color = Color.Gray,
                style = CBMoneyTypography.Body.Small.Regular
            )
        }
        val lol = transactionDetails.transaction.type == fromPeriod(CategoryType.EXPENSE)
        val prefix =  if (lol) "-" else "+"
        Text(
            text = "$prefix${transactionDetails.transaction.amount.formatMoney()} đ",
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
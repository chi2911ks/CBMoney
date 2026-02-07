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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.cbmoney.presentation.components.IconResolver
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.formatMoney
import com.cbmoney.utils.exts.toFormatDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecentTransactionItem(
    modifier: Modifier = Modifier,
    name: String,
    iconColor: String,
    icon: String,
    spent: Long,
    date: Long
) {
    val colorCategory = Color(iconColor.toColorInt())
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
                imageVector = IconResolver.getImageVector(icon),
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
                text = name,
                color = Color.Black,
                style = CBMoneyTypography.Body.Medium.Bold
            )
            Text(
                text = date.toFormatDate(),
                color = Color.Gray,
                style = CBMoneyTypography.Body.Small.Regular
            )
        }
        Text(
            text = "-${spent.formatMoney()} đ",
            color = Color.Red,
            style = CBMoneyTypography.Body.Medium.Bold.copy(
                textAlign = TextAlign.End
            ),
            modifier = Modifier
                .weight(1f)
                .padding(end = Spacing.sm)
        )
    }
}
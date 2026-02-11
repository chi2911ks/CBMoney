package com.cbmoney.presentation.components.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.R
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.formatMoney


@Composable
fun BalanceSummary(
    modifier: Modifier = Modifier,
    isIncome: Boolean = true,
    money: Long = 0L
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .background(
                        color = (if (isIncome) CBMoneyColors.Green2
                        else CBMoneyColors.Red2).copy(0.25f),
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    imageVector = if (isIncome) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                    contentDescription = null,
                    tint = if (isIncome) CBMoneyColors.Green2 else CBMoneyColors.Red2,
                )
            }
            Spacer(Modifier.width(Spacing.sm))
            Text(
                text = (if (isIncome) stringResource(R.string.total_income)
                else stringResource(R.string.total_expense)).uppercase(),
                style = CBMoneyTypography.Body.Small.Bold,
                color = Color.Black
            )
        }
        Spacer(Modifier.height(Spacing.sm))
        Text(
            text = "${money.formatMoney()} ₫",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = CBMoneyTypography.Title.Medium.Bold,
        )
    }




}

@Preview
@Composable
private fun FinanceCardPreview() {

    BalanceSummary(money = 200000000L)


}

@Preview
@Composable
private fun FinanceCardPreview2() {


    BalanceSummary(isIncome = false, money = 9000L)

}
package com.cbmoney.presentation.home.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cbmoney.R
import com.cbmoney.presentation.theme.CBMoneyTheme
import com.cbmoney.utils.formatMoney

@Composable
fun FinanceCard(
    modifier: Modifier = Modifier,
    isIncome: Boolean = true,
    money: Long = 0L
) {

    Box(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(32.dp))


    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterStart)
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = if (isIncome) Color(0xFFDCFCE7) else Color(0xFFFEE2E2),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center

                ) {
                    Icon(
                        imageVector = if (isIncome) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                        contentDescription = null,
                        tint = if (isIncome) Color(0xFF16A34A) else Color(0xFFDC2626),
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = if (isIncome) stringResource(R.string.income) else stringResource(R.string.expense),
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = "${money.formatMoney()} ₫",
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.displayMedium,
            )
        }


    }

}

@Preview
@Composable
private fun FinanceCardPreview() {
    CBMoneyTheme {
        FinanceCard(money = 200000000L)

    }
}

@Preview
@Composable
private fun FinanceCardPreview2() {
    CBMoneyTheme {

        FinanceCard(isIncome = false, money = 9000L)
    }
}
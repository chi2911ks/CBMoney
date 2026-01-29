package com.cbmoney.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cbmoney.R
import com.cbmoney.utils.exts.formatMoney

@Composable
fun CarMoney(onClick: () -> Unit,  modifier: Modifier = Modifier, money: Long = 0) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .background(Color.Black)
            .clickable{onClick()}

    ) {
        GlowBox(
            Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-40).dp),
            color = Color.Green
        )
        GlowBox(
            Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-40).dp, y = 40.dp),
            color = Color.Blue
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_wallet),
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.total_balance),
                    color = Color.White,
                    lineHeight = 20.sp,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Text(
                text = "${money.formatMoney()} ₫",
                lineHeight = 36.sp,
                fontSize = 30.sp,
                color = Color.White,
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(16.dp)

            )
            Row (verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = stringResource(R.string.cash_and_bank),
                    color = Color(0xFF9CA3AF),
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF13EC5B).copy(0.1f))

                ) {
                    Text(
                        "-120.5%",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                    )
                }
            }


        }


    }
}

@Composable
fun GlowBox(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    alpha: Float = 0.2f,
    size: Dp = 160.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha),
                        Color.Transparent
                    )
                )
            )
    )
}

@Preview(showBackground = true)
@Composable
fun CarMoneyPreview(modifier: Modifier = Modifier) {

        CarMoney({}, Modifier)


}
package com.cbmoney.presentation.reports.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.utils.formatShortNumber


@Composable
fun CategoryProcessBar(
    modifier: Modifier = Modifier,
    listData: List<Pair<Long, Color>>,
    size: Dp = 180.dp,
    strokeWidth: Dp = 28.dp,
) {
    val sum = listData.sumOf { it.first }
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val stroke = strokeWidth.toPx()
            val radius = (size.toPx() - stroke) / 2f
            val diameter = radius * 2

            val topLeft = Offset(
                x = (size.toPx() - diameter) / 2f,
                y = (size.toPx() - diameter) / 2f
            )

            val arcSize = Size(diameter, diameter)

            var startAngle = -90f
            listData.forEach {
                val (money, color) = it
                val sweep = money.toFloat() / sum.toFloat() * 360f
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(stroke, cap = StrokeCap.Butt)
                )
                startAngle = startAngle + sweep
            }
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                text = "TỔNG CHI",
                color = Color.Gray,
                style = CBMoneyTypography.Body.Small.Bold
            )
            Text(
                text = formatShortNumber(sum).uppercase(),
                color = Color.Black,
                style = CBMoneyTypography.Title.Large.Bold
            )
        }

    }
}

@Preview
@Composable
private fun CategoryProcessBarPreview() {
    CategoryProcessBar(
        listData = listOf(
            Pair(2000000, Color.Green),
            Pair(6000000, Color.Red),
            Pair(1000000, Color.Blue),
        )

    )
}
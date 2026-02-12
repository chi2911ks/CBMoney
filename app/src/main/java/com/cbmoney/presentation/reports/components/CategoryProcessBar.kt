package com.cbmoney.presentation.reports.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cbmoney.R
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.utils.exts.arcLayout
import com.cbmoney.utils.formatShortNumber


@Composable
fun CategoryProcessBar(
    listData: List<Pair<Long, Color>>,
    modifier: Modifier = Modifier,
    categoryType: CategoryType = CategoryType.EXPENSE,
    size: Dp = 180.dp,
    strokeWidth: Dp = 28.dp,
) {
    if (listData.isEmpty()){
        CategoryProcessBarEmpty(
            modifier = modifier,
            size = size,
            strokeWidth = strokeWidth
        )
        return
    }
    val sum = listData.sumOf { it.first }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val (topLeft, arcSize) = arcLayout(strokeWidth.toPx())
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
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Butt)
                )
                startAngle = startAngle + sweep
            }
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                text = if (categoryType == CategoryType.EXPENSE)
                    stringResource(R.string.total_expense) else
                    stringResource(R.string.total_income),
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

@Composable
fun CategoryProcessBarEmpty(
    modifier: Modifier = Modifier,
    size: Dp = 180.dp,
    strokeWidth: Dp = 28.dp,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            fun drawCircleArc(color: Color, stroke: Float, alpha: Float) {
                val (topLeft, arcSize) = arcLayout(stroke)

                drawArc(
                    color = color.copy(alpha),
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(stroke, cap = StrokeCap.Butt)
                )
            }
            drawCircleArc(
                color = CBMoneyColors.Gray.Gray,
                stroke = strokeWidth.toPx(),
                alpha = 0.25f
            )

            drawCircleArc(
                color = CBMoneyColors.Gray.Gray,
                stroke = 8.dp.toPx(),
                alpha = 0.5f
            )
        }
        Text(
            text = stringResource(R.string.no_transactions),
            color = Color.Gray,
            style = CBMoneyTypography.Body.Small.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
@Preview
@Composable
private fun CategoryProcessBarPreview() {
    CategoryProcessBarEmpty()
//    CategoryProcessBar(
//        listData = listOf(
//            Pair(2000000, Color.Green),
//            Pair(6000000, Color.Red),
//            Pair(1000000, Color.Blue),
//        )
//
//    )
}
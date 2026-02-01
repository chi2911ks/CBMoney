package com.cbmoney.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography


@Composable
fun CircularProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    description: String? = null,
    size: Dp = 180.dp,
    strokeWidth: Dp = 18.dp,
    indicatorColor: Color = CBMoneyColors.Primary.Primary,
    backgroundIndicatorColor: Color = CBMoneyColors.Primary.Primary.copy(alpha = 0.25f),
    style: TextStyle = CBMoneyTypography.Title.Medium.Bold.copy(
        fontSize = 16.sp
    )
) {
    val safeProgress = progress
        .takeIf { it.isFinite() }
        ?.coerceIn(0f, 1f)
        ?: 0f
    val animatedProgress by animateFloatAsState(
        targetValue = safeProgress,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "circular_progress"
    )
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

            drawArc(
                color = backgroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )

            drawArc(
                color = indicatorColor,
                startAngle = -90f,
                sweepAngle = animatedProgress * 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                text = "${(safeProgress * 100).toInt()}%",
                style = style
            )
            if(description != null){
                Text(
                    text = description,
                    color = CBMoneyColors.Neutral.NeutralGray,
                    style = CBMoneyTypography.Title.Small.Regular
                )
            }

        }

    }
}
@Preview
@Composable
private fun CircularProgressBarPreview() {
    CircularProgressBar(
        progress = 0.33f,
        description = "Đã chi"
    )

}
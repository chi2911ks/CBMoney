package com.cbmoney.presentation.components.view


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes

@Composable
fun ProcessBar(
    modifier: Modifier = Modifier,
    progress: Float,
    colorCategory: Color,
    strokeWidth: Dp = 10.dp,
) {
    val safeProgress = progress
        .takeIf { it.isFinite() }
        ?.coerceIn(0f, 1f)
        ?: 0f
    val animatedProgress by animateFloatAsState(
        targetValue = safeProgress,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "progress_bar"
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(strokeWidth)
            .background(CBMoneyColors.Gray.Gray.copy(0.5f), CBMoneyShapes.extraLarge)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .height(strokeWidth)
                .background(colorCategory, CBMoneyShapes.extraLarge)
        )
    }
}
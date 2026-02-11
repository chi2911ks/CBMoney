package com.cbmoney.utils.exts


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.arcLayout(stroke: Float): Pair<Offset, Size> {
    val diameter = size.minDimension - stroke
    val topLeft = Offset(
        (size.width - diameter) / 2f,
        (size.height - diameter) / 2f
    )
    return topLeft to Size(diameter, diameter)
}

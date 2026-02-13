package com.cbmoney.utils.exts

import android.annotation.SuppressLint
import android.os.SystemClock
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cbmoney.presentation.theme.CBMoneyShapes

@SuppressLint("SuspiciousModifierThen")
fun Modifier.rawClickable(onClick: () -> Unit): Modifier =
    this.clickable(
        indication = null,
        interactionSource = MutableInteractionSource()
    ) {
        onClick()
    }
@Composable
fun Modifier.textClickable(onClick: () -> Unit): Modifier
= composed {
    val interactionSource = remember { MutableInteractionSource() }

    this.clickable(
        indication = ripple(),
        interactionSource = interactionSource,
        onClick = onClick,
    )
}
@Composable
fun Modifier.safeClickable(
    debounceTime: Long = 800L,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = null,
    onClick: () -> Unit
): Modifier = composed {
    val currentOnClick by rememberUpdatedState(onClick)
    var lastClickTime by remember { mutableLongStateOf(0L) }

    clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        interactionSource = interactionSource,
        indication = indication,
        onClick = {
            val now = SystemClock.elapsedRealtime()
            if (now - lastClickTime >= debounceTime) {
                currentOnClick()
                lastClickTime = now
            }
        }
    )
}


fun Modifier.shadowCustom(
    elevation: Dp = 4.dp,
    shape: Shape = CBMoneyShapes.extraLarge,
    clip: Boolean = elevation > 0.dp,
    ambientColor: Color = Color.Black.copy(alpha = 0.04f),
    spotColor: Color = Color.Black.copy(alpha = 0.06f)
): Modifier =
    this.shadow(
        elevation = elevation,
        shape = shape,
        clip = clip,
        ambientColor = ambientColor,
        spotColor = spotColor
    )

fun Modifier.dashedBorder(
    color: Color,
    strokeWidth: Dp,
    cornerRadius: Dp,
    dashLength: Float = 10f,
    gapLength: Float = 10f
) = drawBehind {
    drawRoundRect(
        color = color,
        style = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dashLength, gapLength),
                0f
            )
        ),
        cornerRadius = CornerRadius(cornerRadius.toPx())
    )
}



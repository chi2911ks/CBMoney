package com.cbmoney.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cbmoney.ui.theme.GreenColor
import com.cbmoney.ui.theme.LightSageGreen


@Composable
fun ButtonWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = GreenColor,
        contentColor = Color.Black,
    ),
    @DrawableRes iconInt: Int? = null,
    tint: Color = Color.Unspecified
) {
    ButtonPrimary(
        modifier = modifier,
        onClick = onClick,
        text = text,
        colors = colors,
        leadingIcon = {
            if (iconInt != null)
            Icon(
                painter = painterResource(id = iconInt),
                contentDescription = "icon $text",
                modifier = Modifier.size(24.dp),
                tint = tint
            )
        }
    )
}
@Composable
fun ButtonWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = GreenColor,
        contentColor = Color.Black,
    ),
    iconVector: ImageVector? = null,
    tint: Color = Color.Unspecified
) {
    ButtonPrimary(
        modifier = modifier,
        onClick = onClick,
        text = text,
        colors = colors,
        leadingIcon = {
            if (iconVector != null)
            Icon(
                iconVector,
                contentDescription = "icon $text",
                modifier = Modifier.size(24.dp),
                tint = tint
            )
        }
    )
}
@Composable
fun ButtonPrimary(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = GreenColor,
        contentColor = Color.Black,
    ),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val shape = RoundedCornerShape(24.dp)
    Button(
        modifier = modifier,
        shape = shape,
        colors = colors,
        onClick = { onClick() })
    {
        if (leadingIcon != null) leadingIcon()
        Text(
            text = text,
            fontSize = 14.sp,
            style = MaterialTheme.typography.displayMedium
        )
        if (trailingIcon != null) trailingIcon()
    }
}

@Composable
fun OutlineButtonPrimary(
    modifier: Modifier = Modifier,
    text: String,
    colors: ButtonColors,
    @DrawableRes iconInt: Int? = null,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = colors,
        border = BorderStroke(
            width = 1.dp,
            color = LightSageGreen
        ),
    ) {
        if (iconInt != null) {
            Icon(
                painter = painterResource(id = iconInt),
                contentDescription = "icon $text",
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
            Spacer(Modifier.width(4.dp))
        }

        Text(
            text = text,
            fontSize = 16.sp,
            style = MaterialTheme.typography.displayMedium
        )
    }
}
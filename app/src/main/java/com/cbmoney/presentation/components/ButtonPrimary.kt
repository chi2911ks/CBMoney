package com.cbmoney.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cbmoney.ui.theme.LightSageGreen

@Composable
fun ButtonPrimary(
    modifier: Modifier = Modifier,
    text: String,
    colors: ButtonColors,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = colors,
        onClick = { onClick() })
    {
        Text(
            text = text,
            fontSize = 16.sp,
            style = MaterialTheme.typography.displayMedium
        )
    }
}

@Composable
fun OutlineButtonPrimary(
    modifier: Modifier = Modifier,
    text: String,
    colors: ButtonColors,
    @DrawableRes iconInt: Int?=null,
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
        if (iconInt != null){
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
package com.cbmoney.presentation.transaction.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography

@Composable
fun AmountInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = CBMoneyTypography.Headline.Large.Medium,
) {
    Row(
        modifier = modifier.drawBehind {
            val strokeWidth = 2.dp.toPx()
            drawLine(
                color = CBMoneyColors.Primary.Primary.copy(0.5f),
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = strokeWidth
            )
        }
    ){
        //tránh lệch số
        Text(
            text = "₫",
            style = textStyle,
            modifier = Modifier.alpha(0f)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            textStyle = textStyle.copy(
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = "₫",
            style = textStyle
        )
    }
}

@Preview
@Composable
private fun TransTextFieldPreview() {
    AmountInput(
        value = "0",
        onValueChange = {},
    )
}
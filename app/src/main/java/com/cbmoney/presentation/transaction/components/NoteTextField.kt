package com.cbmoney.presentation.transaction.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Notes
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing

@Composable
fun NoteTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    value: String,
    placeholder: String = "",
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = CBMoneyColors.Neutral.Neutral08,
                shape = CBMoneyShapes.large
            )
            .padding(12.dp)
    ) {
        Text(
            text = label.uppercase(),
            color = Color.Gray,
            style = CBMoneyTypography.Body.Small.Regular
        )
        Spacer(modifier = Modifier.height(Spacing.sm))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Notes,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = CBMoneyTypography.Body.Large.Regular,
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = CBMoneyTypography.Body.Large.Regular,
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}
@Preview
@Composable
private fun NoteFieldPreview() {
    NoteTextField(
        label = "Note",
        placeholder = "Enter a note",
        value = "",
        onValueChange = {}
    )
}
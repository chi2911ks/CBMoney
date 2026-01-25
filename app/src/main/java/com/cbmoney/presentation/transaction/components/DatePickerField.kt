package com.cbmoney.presentation.transaction.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing

@Composable
fun DatePickerField(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CBMoneyShapes.large)
            .clickable { onClick() }
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = value,
                style = CBMoneyTypography.Body.Large.Regular
            )
        }
    }
}

@Preview
@Composable
private fun DateFieldPreview() {
    DatePickerField(
        label = "Date",
        value = "2023-01-01",
        onClick = {}
    )
}

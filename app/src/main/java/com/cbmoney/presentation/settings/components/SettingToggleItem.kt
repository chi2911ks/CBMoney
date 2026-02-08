package com.cbmoney.presentation.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.presentation.components.button.ToggleButton
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography


@Composable
fun SettingToggleItem(
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    leadingIcon: ImageVector? = null,
    tintColor: Color = CBMoneyColors.Primary.Primary
) {
    Row(
        modifier = Modifier
            .height(60.dp)
            .clip(RectangleShape)
            .clickable {
                onCheckedChange(!checked)
            }
            .background(Color.White)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(CBMoneyShapes.medium)
                        .background(tintColor.copy(0.1f))
                        .padding(8.dp)
                ) {
                    if (leadingIcon != null) {
                        Icon(
                            imageVector = leadingIcon,
                            contentDescription = null,
                            tint = tintColor
                        )
                    }

                }
                Column (
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = CBMoneyTypography.Title.Small.Medium,
                    )

                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            color = CBMoneyColors.Neutral.NeutralGray,
                            style = CBMoneyTypography.Body.Small.Medium
                        )
                    }
                }

            }
           ToggleButton(
                checked = checked,
                onCheckedChange = onCheckedChange,
                height = 20,
           )

        }

    }
}

@Preview
@Composable
private fun SettingToggleItemPreview() {
    SettingToggleItem("Chế độ tối", checked = true, onCheckedChange = {})
}
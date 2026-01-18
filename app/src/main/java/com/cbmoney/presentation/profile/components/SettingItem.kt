package com.cbmoney.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cbmoney.presentation.theme.CBMoneyTheme
import com.cbmoney.presentation.theme.GreenColor

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    text: String,
    shape: Shape = RectangleShape,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    tintColor: Color = GreenColor,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(50.dp)
            .clip(shape)
            .clickable { onClick() }
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
                        .clip(RoundedCornerShape(12.dp))
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
                Text(
                    text = text,
                    fontSize = 14.sp,
                    color = Color.Black,
                    style = MaterialTheme.typography.displayMedium
                )
            }


            if (trailingIcon != null) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }

        }

    }
}

@Preview
@Composable
private fun SettingItemPreview() {
    CBMoneyTheme {
        SettingItem(
            onClick = {},
            text = "Thông tin cá nhân",
            trailingIcon = Icons.Default.ChevronRight,
            leadingIcon = Icons.Default.Person
        )
    }
}
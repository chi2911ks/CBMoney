package com.cbmoney.presentation.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing

@Composable
fun PreviewCategoryCard(
    name: String,
    color: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        Box(
            modifier = Modifier
                .size(90.dp)
                .border(
                    2.dp,
                    CBMoneyColors.White,
                    CircleShape
                )
                .clip(CircleShape)
                .background(color.copy(0.1f)),
            contentAlignment = Alignment.Center
        ){
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color
            )
        }
        Spacer(Modifier.height(Spacing.sm))
        Text(
            text = name,
            style = CBMoneyTypography.Body.Medium.Medium
        )
    }
}

@Preview
@Composable
private fun PreviewCategoryCardPreview() {
    PreviewCategoryCard(
        name = "Food",
        color = CBMoneyColors.Primary.Primary,
        icon = Icons.Filled.ShoppingBasket
    )
}
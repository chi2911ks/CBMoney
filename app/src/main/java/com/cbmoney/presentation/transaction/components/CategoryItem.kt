package com.cbmoney.presentation.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.cbmoney.domain.model.Category
import com.cbmoney.presentation.common.CategoryIconResolver
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.rawClickable

@Composable
fun CategoryItem(
    selected: Boolean,
    onSelected: () -> Unit,
    category: Category,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .height(80.dp)
            .border(
                2.dp,
                if (selected) CBMoneyColors.Primary.Primary
                else CBMoneyColors.Transparent,
                shape = CBMoneyShapes.extraLarge
            )
            .background(
                if (selected) CBMoneyColors.Primary.Primary.copy(0.1f)
                else CBMoneyColors.Gray.OnGray.copy(0.1f),
                CBMoneyShapes.extraLarge
            ).rawClickable{
                onSelected()
            }
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape),
            contentAlignment =Alignment.Center

        ) {
            Icon(
                imageVector = CategoryIconResolver.iconOf(category.icon),
                contentDescription = null,
                tint = Color(category.iconColor.toColorInt())
            )
        }

        Text(
            text = category.name,
            modifier = Modifier
                .padding(top = Spacing.xs)
                .padding(horizontal = Spacing.xs)
            ,
            textAlign = TextAlign.Center,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
            maxLines = 1,
            style = CBMoneyTypography.Body.Small.Regular
        )
    }

}

@Preview
@Composable
private fun CategoryItemPreview() {

}
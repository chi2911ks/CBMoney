package com.cbmoney.presentation.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
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
import com.cbmoney.domain.constants.DefaultCategoriesMap
import com.cbmoney.domain.model.CategoryType
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing

@Composable
fun CategoryItem(
    selected: Boolean,
    onSelected: (String) -> Unit,
    icon: String,
    name: String,
    color: String,
    modifier: Modifier = Modifier
) {
    val color = color.toColorInt()

    Column(
        modifier = modifier
            .height(80.dp)
            .clip(CBMoneyShapes.large)
            .border(
                2.dp,
                if (selected) CBMoneyColors.Primary.Primary
                else CBMoneyColors.Transparent,
                shape = CBMoneyShapes.large
            )
            .background(
                if (selected) CBMoneyColors.Primary.Primary.copy(0.1f)
                else CBMoneyColors.Gray.OnGray.copy(0.1f),
                CBMoneyShapes.large
            ).clickable{
                onSelected(name)
            }
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(
                    if (selected) Color(color).copy(0.8f)
                    else Color(color).copy(0.2f)
                ),
            contentAlignment =Alignment.Center

        ) {
            Text(
                text = icon,
                modifier = Modifier,
                textAlign = TextAlign.Center,

            )
        }

        Text(
            text = name,
            modifier = Modifier.padding(top = Spacing.xs),
            textAlign = TextAlign.Center,
            style = CBMoneyTypography.Body.Small.Regular
        )
    }

}

@Preview
@Composable
private fun CategoryItemPreview() {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(CBMoneyColors.BackGround.BackgroundPrimary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(Spacing.sm))
        val batches = DefaultCategoriesMap.categories[CategoryType.EXPENSE]?.chunked(3)
        batches?.forEach { batch ->
            Row(
                modifier = Modifier,
            ) {
                Spacer(modifier = Modifier.width(Spacing.sm))
                batch.forEach { item ->
                    CategoryItem(
                        false,
                        {},
                        item["icon"] as String,
                        item["name"] as String,
                        item["color"] as String,
                    )
                    Spacer(modifier = Modifier.width(Spacing.sm))
                }
            }
            Spacer(modifier = Modifier.height(Spacing.sm))
        }
    }


}
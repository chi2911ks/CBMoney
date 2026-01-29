package com.cbmoney.presentation.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.R
import com.cbmoney.presentation.components.IconResolver
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.rawClickable

@Composable
fun IconPicker(
    modifier: Modifier = Modifier,
    nameIcon: String,
    onIconSelected: (String) -> Unit,
) {
    Text(
        text = stringResource(R.string.icon),
        style = CBMoneyTypography.Body.Large.Bold
    )
    Spacer(modifier = Modifier.height(Spacing.sm))
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier
            .heightIn(max = 200.dp),
        verticalArrangement = Arrangement.spacedBy(Spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        IconResolver.CategoryIconMap.forEach { (key, value) ->
            item(key=key){
                IconItem(
                    icon = value,
                    selected = nameIcon == key,
                    onSelected = {
                        onIconSelected(key)
                    }
                )
            }
        }
    }
}
@Composable
fun IconItem(
    icon: ImageVector,
    selected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .border(
                if (selected) 2.dp else 1.dp,
                if (selected) CBMoneyColors.Primary.Primary
                else CBMoneyColors.Border.BorderLight,
                shape = CBMoneyShapes.large
            )
            .background(
                if (selected) CBMoneyColors.Primary.Primary.copy(0.1f)
                else CBMoneyColors.White,
                CBMoneyShapes.large
            )
            .rawClickable {
                onSelected()
            },
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = icon,
            contentDescription = null,
        )
    }
}
@Preview
@Composable
fun IconItemPreview() {
    IconItem(
        icon = Icons.Default.AttachMoney,
        selected = false,
        onSelected = {}
    )
}
package com.cbmoney.presentation.components.dropdown

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography

@Composable
fun <T> DropdownPrimary(
    items: List<T>,
    selected: T,
    itemLabel: @Composable (T?) -> String,
    onSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Row(
            modifier = Modifier
                .clip(CBMoneyShapes.extraLarge)
                .clickable { expanded = true }
                .border(
                    1.dp,
                    CBMoneyColors.Border.BorderLight,
                    shape = CBMoneyShapes.extraLarge
                )
                .padding(horizontal = 12.dp, vertical = 4.dp),

            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = itemLabel(selected),
                style = CBMoneyTypography.Body.Medium.Medium
            )

            Spacer(modifier = Modifier.width(8.dp))

            val rotation by animateFloatAsState(
                targetValue = if (expanded) 180f else 0f,
                label = ""
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.rotate(rotation)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = CBMoneyColors.BackGround.BackgroundPrimary,
            shape = CBMoneyShapes.small
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = itemLabel(item),
                            style = CBMoneyTypography.Body.Medium.Regular
                        )
                    },
                    onClick = {
                        onSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

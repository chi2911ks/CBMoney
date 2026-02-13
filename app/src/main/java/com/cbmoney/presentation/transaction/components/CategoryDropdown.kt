package com.cbmoney.presentation.transaction.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    categories: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Row (
        modifier = Modifier
            .border(
                1.dp,
                CBMoneyColors.Border.BorderLight,
                shape = CBMoneyShapes.extraLarge)
            .clickable {
                expanded = true
            }
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ){
        Text(
            text = selected,
            style = CBMoneyTypography.Body.Medium.Medium
        )
        Spacer(modifier = Modifier.width(8.dp))
        val rotation by animateFloatAsState(if (expanded) 180f else 0f)

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
        shape = CBMoneyShapes.small,
    ) {
        categories.forEach {
            DropdownMenuItem(
                text = { Text(it, style = CBMoneyTypography.Body.Medium.Regular) },
                onClick = {
                    onSelected(it)
                    expanded = false
                },


            )
        }
    }

}

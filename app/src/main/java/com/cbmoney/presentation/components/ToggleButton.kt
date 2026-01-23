package com.cbmoney.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyColors.Neutral.NeutralGray
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.Spacing

@Composable
fun ToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    height: Int = 40,
) {
    Box (
        modifier = modifier
            .size((height*2).dp, height.dp)
            .clip(CBMoneyShapes.extraLarge)
            .clickable {
                onCheckedChange(!checked)
            }
            .background(if (checked) CBMoneyColors.Primary.Primary else NeutralGray)
            .padding(horizontal = Spacing.xs),
    ){
        Box(
            modifier = Modifier
                .size((height-5).dp)
                .clip(CircleShape)
                .background(Color.White)
                .align(if (checked) Alignment.CenterEnd else Alignment.CenterStart)
        )
    }

}

@Preview
@Composable
private fun ToggleButtonPreview() {
    Column {
        ToggleButton(checked = true, onCheckedChange = {})
        Spacer(Modifier.height(8.dp))
        ToggleButton(checked = false, onCheckedChange = {})
    }

}

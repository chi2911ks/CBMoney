package com.cbmoney.presentation.category.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cbmoney.R
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.hexToColor
import com.cbmoney.utils.exts.rawClickable
import com.cbmoney.utils.exts.toHex
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ColorPickerDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onColorChanged: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    val controller = rememberColorPickerController()
    if (showDialog){
        Dialog(
            onDismissRequest = {
                onDismiss()
            },

            ) {
            Column (
                modifier = modifier
                    .clip(CBMoneyShapes.large)
                    .background(CBMoneyColors.White)
                    .padding(Spacing.md)
            ){
                HsvColorPicker(
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth(),
                    controller = controller,
                    onColorChanged = { colorEnvelope ->
                        onColorChanged(colorEnvelope.color)
                    }
                )
                AlphaSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(25.dp),
                    controller = controller,
                )
                BrightnessSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(25.dp),
                    controller = controller,
                )
            }
        }
    }

}
@Composable
fun ColorPicker(
    currentColor: String,
    onColorChanged: (String) -> Unit,
) {
    val listColor = CBMoneyColors.CategoryColorList.list
    var showDialog by remember { mutableStateOf(false) }
    ColorPickerDialog(
        showDialog = showDialog,
        onDismiss = {
            showDialog = false
        },
        onColorChanged = {
            onColorChanged(it.toHex())
        }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = stringResource(R.string.color),
            style = CBMoneyTypography.Body.Large.Bold
        )
        Row(
            modifier = Modifier
                .rawClickable {
                    showDialog = true
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Default.ColorLens,
                contentDescription = null,
                tint = CBMoneyColors.Green2
            )
            Text(
                text = stringResource(R.string.customize),
                color = CBMoneyColors.Green2,
                style = CBMoneyTypography.Body.Small.Bold
            )
        }


    }

    Spacer(modifier = Modifier.height(Spacing.sm))
    LazyVerticalGrid(
        columns = GridCells.Fixed(10)
    ) {
        items(listColor.size){index->
            val color = listColor[index].toHex()
            ColorItem(
                selected = currentColor == color,
                color = color.hexToColor(),
                onSelected = {
                    onColorChanged(color)
                }
            )
        }
    }
}
@Composable
fun ColorItem(
    selected: Boolean,
    color: Color,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .rawClickable {
                onSelected()
            }
            .border(
                2.dp,
                if (selected) color else Color.Transparent,
                CircleShape
            )
            .padding(Spacing.xs),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .size(25.dp)
                .clip(CircleShape)
                .background(color)
        )
    }
}

@Preview
@Composable
private fun ColorItemPreview() {
    Row{
        ColorItem(
            selected = true,

            color = Color.Red,
            onSelected = {}
        )
        ColorItem(
            selected = false,

            color = Color.Green,
            onSelected = {}
        )
        ColorItem(
            selected = false,

            color = Color.Black,
            onSelected = {}
        )
    }

}
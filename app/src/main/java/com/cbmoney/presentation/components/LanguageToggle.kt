package com.cbmoney.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.presentation.theme.CBMoneyColors

@Composable
fun LanguageToggle(
    modifier: Modifier = Modifier,
    initialIsVN: Boolean = true,
    onLanguageChange: (Boolean) -> Unit
) {
    var isVN by remember { mutableStateOf(initialIsVN) }
    Row(
        modifier = modifier
            .background(
                color = CBMoneyColors.Gray.Gray,
                shape = RoundedCornerShape(50)
            )
            .padding(4.dp)
    ) {
        LanguageItem(text = "VN", selected = isVN) {
            isVN = true
            onLanguageChange(true)
        }
        LanguageItem(text = "EN", selected = !isVN) {
            isVN = false
            onLanguageChange(false)
        }
    }
}

@Composable
fun LanguageItem(text: String, selected: Boolean, onCLick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(if (selected) Color.White else Color.Transparent)
            .clickable(onClick = onCLick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) Color.Black else Color.Gray,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
private fun LanguageTogglePreview() {

        LanguageToggle() {

        }


}
package com.cbmoney.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cbmoney.presentation.home.components.model.MonthlyData

@Composable
fun MonthlySpendingCard(
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    listData: List<MonthlyData>,
    modifier: Modifier = Modifier
) {
    val labelMonthly = listData[selectedIndex].label
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Chi tiêu tháng này",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = labelMonthly,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            BarChart(
                selectedIndex = selectedIndex,
                onSelected = { onSelected(it)},
                listData = listData
            )
        }
    }
}

@Composable
fun BarChart(
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    listData: List<MonthlyData>,
) {

    val scrollState = rememberScrollState()
    val maxValue = (listData.maxOfOrNull { maxOf(it.income, it.expense) } ?: 1) + 10
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        verticalAlignment = Alignment.Bottom
    ) {
        listData.forEachIndexed { index, monthlyData ->
            BarItem(
                onClick = {
                    onSelected(index)
                },
                isSelected = index == selectedIndex,
                maxValue = maxValue,
                monthlyData = monthlyData
            )

        }
    }
}

@Composable
fun BarItem(
    onClick: () -> Unit,
    isSelected: Boolean = false,
    maxValue: Long,
    monthlyData: MonthlyData
) {
    val maxHeight = 120
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(
                RoundedCornerShape(12.dp)
            )
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .background(
                    color = if (isSelected) Color.Gray.copy(0.2f) else Color.Transparent,
                )
                .padding(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(
                            ((monthlyData.income * maxHeight) / maxValue).coerceAtLeast(1L)
                                .toInt().dp
                        )
                        .background(
                            Color(0xFF16A34A),
                            shape = RoundedCornerShape(12.dp)
                        )
                )
                Spacer(Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(((monthlyData.expense * maxHeight) / maxValue).toInt().dp)
                        .background(
                            Color(0xFFDC2626),
                            shape = RoundedCornerShape(
                                12.dp
                            )
                        )
                )
            }
            Spacer(Modifier.height(8.dp))
        }

        Text(
            text = monthlyData.label,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}


@Preview
@Composable
private fun BarChartPreview() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val listData = listOf(
        MonthlyData("Tháng 1", 1000000000L, 20000000L),
        MonthlyData("Tháng 2", 7000000L, 2000000L),
        MonthlyData("Tháng 3", 20000000L, 10000000L),
    )
    MonthlySpendingCard(
        selectedIndex = selectedIndex,
        onSelected = { selectedIndex = it },
        listData
    )
}

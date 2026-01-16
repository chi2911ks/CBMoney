package com.cbmoney.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cbmoney.presentation.navigation.BottomNavigation

@Composable
fun HomeScreen(navController: NavController) {

    Scaffold(bottomBar = {
        BottomNavigation(navController)
    })
    { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            Column(
                modifier = Modifier.align(Alignment.Center).padding(16.dp)


            ) {
                WeeklySpendingCard(weeklyData)
            }
        }

    }

}

data class WeeklySpending(
    val day: String,
    val amount: Int
)

val weeklyData = listOf(
    WeeklySpending("T2", 120_000),
    WeeklySpending("T3", 300_000),
    WeeklySpending("T4", 0),
    WeeklySpending("T5", 150_000),
    WeeklySpending("T6", 520_000),
    WeeklySpending("T7", 200_000),
    WeeklySpending("CN", 100_000),
)

@Composable
fun WeeklySpendingCard(
    data: List<WeeklySpending>
) {
    var selectedIndex by remember { mutableStateOf(4) } // T6

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Chi tiêu hàng tuần", fontWeight = FontWeight.Bold)
                    Text("Weekly Spending", fontSize = 12.sp, color = Color.Gray)
                }
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = null
                )
            }

            Spacer(Modifier.height(16.dp))

            // Biểu đồ
            WeeklyBarChart(
                data = data,
                selectedIndex = selectedIndex,
                onSelect = { selectedIndex = it }
            )

            Spacer(Modifier.height(12.dp))

            // Day selector
            DaySelector(
                data = data,
                selectedIndex = selectedIndex,
                onSelect = { selectedIndex = it }
            )
        }
    }
}

@Composable
fun WeeklyBarChart(
    data: List<WeeklySpending>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    val maxAmount = data.maxOf { it.amount }.coerceAtLeast(1)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEachIndexed { index, item ->
            val heightRatio = item.amount / maxAmount.toFloat()

            Box(
                modifier = Modifier
                    .width(24.dp)
                    .fillMaxHeight(heightRatio)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (index == selectedIndex) Color(0xFF22C55E)
                        else Color(0xFFE5E7EB)
                    )
                    .clickable { onSelect(index) }
            )
        }
    }
}

@Composable
fun DaySelector(
    data: List<WeeklySpending>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        data.forEachIndexed { index, item ->
            Text(
                text = item.day,
                fontWeight = if (index == selectedIndex) FontWeight.Bold else FontWeight.Normal,
                color = if (index == selectedIndex) Color(0xFF22C55E) else Color.Gray,
                modifier = Modifier.clickable { onSelect(index) }
            )
        }
    }
}

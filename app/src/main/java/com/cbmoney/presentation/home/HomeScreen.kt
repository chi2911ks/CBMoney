package com.cbmoney.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cbmoney.R
import com.cbmoney.presentation.components.ButtonPrimary
import com.cbmoney.presentation.components.ButtonWithIcon
import com.cbmoney.presentation.home.components.CarMoney
import com.cbmoney.presentation.home.components.FinanceCard
import com.cbmoney.presentation.home.components.MonthlySpendingCard
import com.cbmoney.presentation.home.components.model.MonthlyData
import com.cbmoney.ui.theme.CBMoneyTheme
import com.cbmoney.ui.theme.GreenColor

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFf6f8f6))

            .statusBarsPadding()


    ) {
        HomeScreenContent()
    }
}

@Composable
fun HomeScreenContent() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val listData = listOf(
        MonthlyData("Tháng 1", 10000000L, 20000000L),
        MonthlyData("Tháng 2", 7000000L, 2000000L),
        MonthlyData("Tháng 3", 20000000L, 10000000L),
        MonthlyData("Tháng 4", 10000000L, 20000000L),
        MonthlyData("Tháng 5", 7000000L, 2000000L),
        MonthlyData("Tháng 6", 20000000L, 10000000L),
        MonthlyData("Tháng 7", 10000000L, 20000000L),
        MonthlyData("Tháng 8", 7000000L, 2000000L),
        MonthlyData("Tháng 9", 20000000L, 10000000L),
        MonthlyData("Tháng 10", 10000000L, 20000000L),
        MonthlyData("Tháng 11", 7000000L, 2000000L),
        MonthlyData("Tháng 12", 20000000L, 10000000L),
    )
    val income = listData[selectedIndex].income
    val expense = listData[selectedIndex].expense

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 8.dp)
            .padding(bottom = 16.dp),
    ) {

        HeaderSection()
        CarMoney(
            onClick = {},
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FinanceCard(
                modifier = Modifier.weight(1f),
                money = income
            )
            Spacer(Modifier.width(8.dp))
            FinanceCard(
                modifier = Modifier.weight(1f),
                isIncome = false,
                money = expense
            )
        }
        MonthlySpendingCard(
            selectedIndex,
            { selectedIndex = it },
            listData,
            Modifier
                .padding(top = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            ButtonWithIcon(
                modifier =  Modifier
                    .weight(1f),
                text = stringResource(R.string.additional_expenses),
                onClick = {},
                iconVector = Icons.Default.Add
            )
            Spacer(Modifier.width(4.dp))
            ButtonWithIcon(
                modifier =  Modifier
                    .weight(1f),
                text = stringResource(R.string.additional_income),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                ),
                iconVector = Icons.Default.AttachMoney,
                tint = MaterialTheme.colorScheme.primary
            )

        }
    }

}

@Composable
fun HeaderSection(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    text = "Xin chào,",
                    color = Color(0xFF6B7280),
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "Minh Anh",
                    color = Color.Black,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.displayMedium
                )
            }

        }


        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .background(Color.White)
                .size(40.dp),

            contentAlignment = Alignment.Center

        ) {
            Icon(
                Icons.Default.Notifications,
                contentDescription = null,
                tint = Color.Black,
            )
        }

    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    CBMoneyTheme {
        HomeScreen()
    }
}
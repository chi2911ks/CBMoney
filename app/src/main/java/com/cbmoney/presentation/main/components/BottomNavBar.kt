package com.cbmoney.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.R
import com.cbmoney.presentation.main.model.MainTab
import com.cbmoney.ui.theme.CBMoneyTheme
import com.cbmoney.ui.theme.Gray
import com.cbmoney.ui.theme.GreenColor

@Composable
fun BottomNavBar(
    currentTab: MainTab,
    onTabChange: (MainTab) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(100.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(70.dp)
                .background(MaterialTheme.colorScheme.surface)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically

        ) {
            MainTab.entries.forEachIndexed { index, tab ->
                val isSelected = tab == currentTab
                Column(
                    modifier = Modifier

                        .weight(1f)
                        .then(
                            when (index) {
                                0 -> Modifier.padding(start = 20.dp)
                                1 -> Modifier.padding(end = 20.dp)
                                2 -> Modifier.padding(start = 20.dp)
                                3 -> Modifier.padding(end = 20.dp)
                                else -> Modifier
                            }
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(
                            interactionSource = null
                        ) {
                            onTabChange(tab)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val title = when(tab){
                        MainTab.HOME -> stringResource(R.string.home)
                        MainTab.REPORTS -> stringResource(R.string.reports)
                        MainTab.BUDGET -> stringResource(R.string.budget)
                        MainTab.PROFILE -> stringResource(R.string.profile)
                    }

                    Icon(
                        painter = painterResource(tab.iconResId),
                        contentDescription = title,
                        tint = if (isSelected) GreenColor else Gray,
                        modifier = Modifier.padding(10.dp)
                    )
                    Text(
                        title,
                        color = if (isSelected) GreenColor else Gray,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(60.dp)
                .clip(CircleShape)
                .background(GreenColor)
                .clickable{
                    onAddClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun BottomNavBarPreview() {
    CBMoneyTheme {
        BottomNavBar(MainTab.HOME, {}, {})
    }
}
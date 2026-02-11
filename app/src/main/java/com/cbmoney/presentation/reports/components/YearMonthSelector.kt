package com.cbmoney.presentation.reports.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cbmoney.R
import com.cbmoney.presentation.theme.CBMoneyTypography
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YearMonthSelector(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    onYearMonthChange: (YearMonth) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        IconButton(onClick = {
            onYearMonthChange(yearMonth.minusMonths(1))
        }) {
            Icon(Icons.Default.ChevronLeft, null)
        }

        Text(
            text = "${stringResource(R.string.month)} ${yearMonth.monthValue}, ${yearMonth.year}",
            modifier = Modifier.clickable { },
            style = CBMoneyTypography.Body.Medium.Bold
        )

        IconButton(onClick = {
            onYearMonthChange(yearMonth.plusMonths(1))
        }) {
            Icon(Icons.Default.ChevronRight, null)
        }
    }
}
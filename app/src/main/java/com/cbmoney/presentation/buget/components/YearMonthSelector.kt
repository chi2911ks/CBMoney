package com.cbmoney.presentation.buget.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.DateUtils
import com.cbmoney.utils.exts.rawClickable
import com.cbmoney.utils.exts.shadowCustom
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
            .fillMaxWidth()
            .shadowCustom()
            .background(CBMoneyColors.White, CBMoneyShapes.extraLarge)
            .padding(horizontal = Spacing.md, vertical = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ChevronLeft,
            contentDescription = null,
            tint = CBMoneyColors.Neutral.NeutralGray,
            modifier = Modifier.rawClickable {
                onYearMonthChange(yearMonth.minusMonths(1))
            }

        )
        Text(
            text = DateUtils.getYearMonthFormat(yearMonth = yearMonth),
            modifier = Modifier.weight(1f),
            style = CBMoneyTypography.Body.Medium.Bold.copy(
                textAlign = TextAlign.Center
            ),
        )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = CBMoneyColors.Neutral.NeutralGray,
            modifier = Modifier.rawClickable {
                onYearMonthChange(yearMonth.plusMonths(1))
            }
        )
    }
}
package com.cbmoney.presentation.transaction.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cbmoney.R
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.toFormatDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateInputDialog(
    datePickerState: DatePickerState,
) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Text(
                    text = stringResource(R.string.select),
                    color = CBMoneyColors.Primary.Primary,
                    style = CBMoneyTypography.Body.Large.Bold,
                    modifier = Modifier
                        .padding(Spacing.sm)
                        .clickable { showDialog = false }
                )
            },
            dismissButton = {
                Text(
                    text = stringResource(R.string.cancel),
                    color = CBMoneyColors.Black,
                    style = CBMoneyTypography.Body.Large.Bold,
                    modifier = Modifier
                        .padding(Spacing.sm)
                        .clickable { showDialog = false }
                )
            },
            colors = DatePickerDefaults.colors(
                containerColor = CBMoneyColors.BackGround.BackgroundPrimary,
            )
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = CBMoneyColors.BackGround.BackgroundPrimary,

                    // ngày được chọn
                    selectedDayContainerColor = CBMoneyColors.Primary.Primary.copy(0.3f),
                    selectedDayContentColor = CBMoneyColors.Text.TextPrimary,

                    // ngày hôm nay (vòng tròn)
                    todayDateBorderColor = CBMoneyColors.Primary.Primary,
                    todayContentColor = CBMoneyColors.Text.TextPrimary,

                    // ngày bình thường
                    dayContentColor = CBMoneyColors.Text.TextPrimary,
                    weekdayContentColor = CBMoneyColors.Text.TextSecondary
                )
            )

        }
    }
    val selectedMillis = datePickerState.selectedDateMillis
    var formattedDate: String? = null
    formattedDate = selectedMillis?.toFormatDate() ?: System.currentTimeMillis().toFormatDate()


    DatePickerField(
        label = stringResource(R.string.date).uppercase(),
        value = formattedDate,
        onClick = { showDialog = true },
    )
}
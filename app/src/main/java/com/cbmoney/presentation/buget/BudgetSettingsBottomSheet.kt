package com.cbmoney.presentation.buget

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.domain.constants.DefaultCategories
import com.cbmoney.presentation.buget.components.ExpenditureCategoryItem
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.formatDigit
import com.cbmoney.utils.exts.formatMoney
import com.cbmoney.utils.exts.rawClickable
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BudgetSettingsBottomSheet(modifier: Modifier = Modifier) {
    BudgetSettingsContent()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BudgetSettingsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .statusBarsPadding()
            .padding(horizontal = Spacing.md)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = "Cài đặt Ngân sách",
                style = CBMoneyTypography.Body.Large.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
            Text(
                text = "Lưu",
                color = CBMoneyColors.Primary.Primary,
                style = CBMoneyTypography.Body.Large.Bold,
                modifier = Modifier.align(Alignment.CenterEnd)
            )

        }
        Spacer(modifier = Modifier.height(Spacing.md))
        var yearMonth by remember {
            mutableStateOf(YearMonth.now())
        }
        ChooseDate(
            yearMonth = yearMonth,
            onYearMonthChange = { newYearMonth ->
                yearMonth = newYearMonth
                println("Selected: $newYearMonth")
            }
        )
        Spacer(modifier = Modifier.height(Spacing.md))
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = CBMoneyShapes.extraLarge,
            colors = CardDefaults.cardColors(
                containerColor = CBMoneyColors.White,
            )
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.md),
            ) {
                Text(
                    text = "TỔNG NGÂN SÁCH",
                    style = CBMoneyTypography.Body.Large.Bold,
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var cc by remember {
                        mutableStateOf("")
                    }

                    BasicTextField(
                        value = cc.formatMoney(),
                        onValueChange = {
                            val input = it.formatDigit()
                            if (input != null) {
                                cc = input.toString()
                            }
                        },
                        textStyle = CBMoneyTypography.Title.Large.Bold.copy(
                            color = CBMoneyColors.Primary.Primary
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        decorationBox = {innerTextField->
                            if (cc.isEmpty()){
                                Text(
                                    text = "Chưa cài đặt",
                                    style = CBMoneyTypography.Title.Large.Regular.copy(
                                        color = CBMoneyColors.Neutral.NeutralGray,
                                        fontStyle = FontStyle.Italic
                                    ),

                                )
                            }
                            innerTextField()
                        }
                    )
                    if (!cc.isEmpty()){
                        Text(
                            text = "đ",
                            style = CBMoneyTypography.Title.Large.Bold.copy(
                                color = CBMoneyColors.Primary.Primary
                            ),
                        )
                    }
                }
                HorizontalDivider(
                    thickness=1.dp,
                    color = CBMoneyColors.Neutral.NeutralGray.copy(0.5f)
                )
                Spacer(modifier = Modifier.height(Spacing.md))
                Text(
                    text = "Chi tiết phân bổ",
                    style = CBMoneyTypography.Body.Medium.Bold,
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Tổng ngân sách theo hạng mục: ",
                        style = CBMoneyTypography.Body.Small.Regular,
                    )
                    Text(
                        text = "100.000.000đ",
                        style = CBMoneyTypography.Body.Small.Bold,
                    )
                }
            }
        }
        ExpenditureCategory(

        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChooseDate(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    onYearMonthChange: (YearMonth) -> Unit
) {
    val text = remember(yearMonth) {
        val start = yearMonth.atDay(1)
        val end = yearMonth.atEndOfMonth()
        "%02d/%d (%02d/%02d - %02d/%02d)".format(
            yearMonth.monthValue,
            yearMonth.year,
            start.dayOfMonth,
            start.monthValue,
            end.dayOfMonth,
            end.monthValue
        )
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(CBMoneyShapes.extraLarge)
            .background(CBMoneyColors.White)
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
            text = text,
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


@Composable
fun ExpenditureCategory(
    modifier: Modifier = Modifier,
//    onBudgetChange: () -> Unit,
) {
    val budgets = remember {
        mutableStateMapOf(
            *DefaultCategories.EXPENSE_CATEGORIES.map { it.name to 0L }.toTypedArray()
        )
    }
    val totalBudget = budgets.values.sum()

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Hạng mục chi tiêu: $totalBudget",
            modifier = Modifier
        )
        LazyColumn {
            items(
                items = DefaultCategories.EXPENSE_CATEGORIES,
                key = { it.name }
            ){cate->
                ExpenditureCategoryItem(
                    category = cate,
                    budget = (budgets[cate.name] ?: 0).toString(),
                    onBudgetChange = {
                        budgets[cate.name] = it.toLong()
                    }
                )
            }
        }
    }

}
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun BudgetSettingsBottomSheetPreview() {
    BudgetSettingsBottomSheet()
//
}

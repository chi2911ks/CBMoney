package com.cbmoney.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.R
import com.cbmoney.presentation.profile.components.SettingItem
import com.cbmoney.presentation.settings.components.SettingToggleItem
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyColors.Neutral.NeutralGray
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.utils.exts.getLanguageCode

@Composable
fun SettingsScreen(
    onNavigationBack: () -> Unit,
    onShowLanguageBottomSheet: () -> Unit
) {
    SettingScreenContent(onNavigationBack = onNavigationBack, onShowLanguageBottomSheet=onShowLanguageBottomSheet)
}

@Composable
fun SettingScreenContent(
    onNavigationBack: () -> Unit,
    onShowLanguageBottomSheet: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        onNavigationBack()
                    }
            )
            Text(
                text = stringResource(R.string.app_setting),
                modifier = Modifier
                    .align(Alignment.Center),
                style = CBMoneyTypography.Body.Large.Medium,
            )
        }
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        CustomizeContent(
            onShowLanguageBottomSheet = onShowLanguageBottomSheet
        )
        FinanceContent(onShowLanguageBottomSheet)
        NotificationContent()
    }
}

@Composable
private fun FinanceContent(
    onShowCurrencyBottomSheet: () -> Unit
) {
    Text(
        text = stringResource(R.string.finance).uppercase(),
        color = NeutralGray,
        style = CBMoneyTypography.Title.Small.Medium,
        modifier = Modifier.padding(8.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        SettingItem(
            title = stringResource(R.string.finance),
            subtitle = "VND",
            leadingIcon = Icons.Default.AttachMoney,
            trailingIcon = Icons.Default.ChevronRight
        ) {
            onShowCurrencyBottomSheet()
        }
    }
}

@Composable
fun CustomizeContent(
    onShowLanguageBottomSheet: () -> Unit
) {
    val context = LocalContext.current
    val languageText = stringResource(
        if (context.getLanguageCode() == "vi")
            R.string.vietnamese
        else
            R.string.english
    )
    Text(
        text = stringResource(R.string.customize).uppercase(),
        color = NeutralGray,
        style = CBMoneyTypography.Title.Small.Medium,
        modifier = Modifier.padding(8.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        SettingItem(
            title = stringResource(R.string.language),
            subtitle = languageText,
            leadingIcon = Icons.Default.Language,
            trailingIcon = Icons.Default.ChevronRight
        ){
            onShowLanguageBottomSheet()
        }
        var isDarkTheme by remember { mutableStateOf(false) }
        SettingToggleItem(
            stringResource(R.string.dark_mode),
            checked = isDarkTheme,
            onCheckedChange = {
                isDarkTheme = it
            },
            leadingIcon = Icons.Default.DarkMode,
        )
    }
}

@Composable
fun NotificationContent(

) {
    var isNotification by remember { mutableStateOf(false) }
    Text(
        text = stringResource(R.string.notification).uppercase(),
        color = NeutralGray,
        style = CBMoneyTypography.Title.Small.Medium,
        modifier = Modifier.padding(8.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        SettingToggleItem(
            stringResource(R.string.expense_reminder),
            checked = isNotification,
            onCheckedChange = {
                isNotification = it
            },
            leadingIcon = Icons.Default.NotificationsActive,
        )
    }
}
@Preview
@Composable
private fun SettingScreenContentPreview() {

        SettingScreenContent({}, {})

}
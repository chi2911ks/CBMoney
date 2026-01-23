package com.cbmoney.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpCenter
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.R
import com.cbmoney.presentation.components.ButtonPrimary
import com.cbmoney.presentation.profile.components.EditableAvatar
import com.cbmoney.presentation.profile.components.SettingItem
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyColors.Neutral.NeutralGray
import com.cbmoney.presentation.theme.CBMoneyTypography
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navigateToPersonInfo: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToHelpCenter: () -> Unit,
    logout: ()-> Unit,
    profileViewModel: ProfileViewModel = koinViewModel()
) {
    val uiState by profileViewModel.viewState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        profileViewModel.singleEvent.collect {
            when(it){
                is ProfileEvent.LogOut -> logout()
            }
        }

    }
    ProfileScreenContent(
        uiState = uiState,
        navigateToPersonInfo = navigateToPersonInfo,
        navigateToSettings = navigateToSettings,
        navigateToHelpCenter = navigateToHelpCenter,
        logout = {
            profileViewModel.processIntent(it)
        }
    )

}

@Composable
fun ProfileScreenContent(
    uiState: ProfileState,
    navigateToPersonInfo: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToHelpCenter: () -> Unit,
    logout: (ProfileIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary),
    ) {
        HeaderSection(uiState)
        UserProfileSettings(
            navigateToPersonInfo = navigateToPersonInfo,
            navigateToSettings = navigateToSettings,
            navigateToHelpCenter = navigateToHelpCenter
        )
        ButtonPrimary(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(R.string.logout),
            leadingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = null,
                    tint = Color.Red
                )
            },
            onClick = {
                logout(ProfileIntent.LogOut)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red.copy(0.1f),
                contentColor = Color.Red,
            ),
        )

    }
}

@Composable
fun HeaderSection(
    uiState: ProfileState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hồ sơ",
            fontSize = 16.sp,
            style = CBMoneyTypography.Body.Large.Bold
        )
        Spacer(Modifier.height(16.dp))
        EditableAvatar(
            imageURL = uiState.imageURL,
            onEditClick = {},
            onAvatarClick = {}
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = uiState.name,
            style = CBMoneyTypography.Title.Medium.Medium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = uiState.email,
            style = CBMoneyTypography.Body.Small.Medium
        )
    }
}

@Composable
fun UserProfileSettings(
    navigateToPersonInfo: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToHelpCenter: () -> Unit,
) {

    Text(
        text = stringResource(R.string.settings_account).uppercase(),
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
            title = stringResource(R.string.person_info),
            leadingIcon = Icons.Default.Person,
            trailingIcon = Icons.Default.ChevronRight,
        ) {
            navigateToPersonInfo()
        }
        SettingItem(
            title = stringResource(R.string.bank_account),
            leadingIcon = Icons.Default.Wallet,
            trailingIcon = Icons.Default.ChevronRight,
        ) {

        }
    }
    Text(
        text = stringResource(R.string.application).uppercase(),
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
            title = stringResource(R.string.app_setting),
            leadingIcon = Icons.Default.Settings,
            trailingIcon = Icons.Default.ChevronRight,
            tintColor = Color.Black
        ) {
            navigateToSettings()
        }
        SettingItem(
            title = stringResource(R.string.help_center),
            leadingIcon = Icons.AutoMirrored.Filled.HelpCenter,
            trailingIcon = Icons.Default.ChevronRight,
            tintColor = Color.Black
        ) {
            navigateToHelpCenter()
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {

        ProfileScreen({}, {}, {}, {})

}
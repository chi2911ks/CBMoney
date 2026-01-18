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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cbmoney.R
import com.cbmoney.presentation.components.ButtonPrimary
import com.cbmoney.presentation.profile.components.EditableAvatar
import com.cbmoney.presentation.profile.components.SettingItem
import com.cbmoney.presentation.theme.CBMoneyTheme
import com.cbmoney.presentation.theme.NeutralGray

@Composable
fun ProfileScreen() {

    ProfileScreenContent()

}

@Composable
fun ProfileScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            ,
    ) {
        HeaderSection()
        UserProfileSettings()
        ButtonPrimary(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(R.string.logout),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = null,
                    tint = Color.Red
                )
            },
            onClick = {},
            colors  = ButtonDefaults.buttonColors(
                containerColor = Color.Red.copy(0.1f),
                contentColor = Color.Red,
            ),
        )

    }
}

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hồ sơ",
            fontSize = 16.sp,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(Modifier.height(16.dp))
        EditableAvatar(
            imageRes = R.drawable.avatar_boy,
            onEditClick = {},
            onAvatarClick = {}
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Nguyễn Văn A",
            fontSize = 24.sp,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "nguyenvana@gmail.com",
            fontSize = 12.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun UserProfileSettings() {

    Text(
        text = stringResource(R.string.settings_account).uppercase(),
        color = NeutralGray,
        fontSize = 14.sp,
        style = MaterialTheme.typography.displayMedium,
        modifier = Modifier.padding(8.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        SettingItem(
            text = stringResource(R.string.person_info),
            leadingIcon = Icons.Default.Person,
            trailingIcon = Icons.Default.ChevronRight,
        ){

        }
        SettingItem(
            text = stringResource(R.string.bank_account),
            leadingIcon = Icons.Default.Wallet,
            trailingIcon = Icons.Default.ChevronRight,
        ){

        }
    }
    Text(
        text = stringResource(R.string.application).uppercase(),
        color = NeutralGray,
        fontSize = 14.sp,
        style = MaterialTheme.typography.displayMedium,
        modifier = Modifier.padding(8.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        SettingItem(
            text = stringResource(R.string.app_setting),
            leadingIcon = Icons.Default.Settings,
            trailingIcon = Icons.Default.ChevronRight,
            tintColor = Color.Black
        ){

        }
        SettingItem(
            text = stringResource(R.string.help_center),
            leadingIcon = Icons.AutoMirrored.Filled.HelpCenter,
            trailingIcon = Icons.Default.ChevronRight,
            tintColor = Color.Black
        ){

        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    CBMoneyTheme {
        ProfileScreen()
    }
}
package com.cbmoney.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cbmoney.R
import com.cbmoney.presentation.login.AuthProviders
import com.cbmoney.presentation.components.ButtonPrimary
import com.cbmoney.presentation.components.LanguageToggle
import com.cbmoney.presentation.components.OutlinedText
import com.cbmoney.ui.theme.Gray
import com.cbmoney.ui.theme.GreenColor
import com.cbmoney.ui.theme.NeutralGray
import com.cbmoney.utils.setAppLocale

@Composable
fun RegisterScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
    ) {
        LanguageToggle(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(horizontal = 16.dp),
            initialIsVN = true,
        ) { isVN ->
            val lang = if (isVN) "" else "en"
            context.setAppLocale(lang)
        }
        Column(
            modifier = Modifier.padding(
                top = 55.dp,
                start = 16.dp,
                end = 16.dp
            ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_coin),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(R.string.create_account),
                fontSize = 32.sp,
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.signup_manage),
                fontSize = 16.sp,
                color = NeutralGray,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(32.dp))
            SignUpForm(Modifier.fillMaxWidth(), onBackClick = onBackClick) {

            }
            AuthProviders(
                modifier = Modifier.padding(bottom = 25.dp)
            )
        }

    }
}

@Composable
fun SignUpForm(modifier: Modifier = Modifier, onBackClick: () -> Unit = {}, onRegister: () -> Unit = {}) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        OutlinedText(
            value = fullName,
            label = stringResource(R.string.full_name),
            onValueChange = { fullName = it },
            placeholderText = "Alice Ninja",
            leadingIcon = {
                Icon(Icons.Default.AccountCircle, "icon user", tint = NeutralGray)
            }

        )
        Spacer(Modifier.height(8.dp))
        OutlinedText(
            value = email,
            label = stringResource(R.string.email),
            onValueChange = { email = it },
            placeholderText = "name@example.com",
            leadingIcon = {
                Icon(Icons.Default.Email, "icon email", tint = NeutralGray)
            }

        )
        Spacer(Modifier.height(8.dp))
        OutlinedText(
            value = password,
            label = stringResource(R.string.password),
            onValueChange = { password = it },
            placeholderText = "******",
            leadingIcon = {
                Icon(Icons.Outlined.Lock, "icon lock", tint = NeutralGray)
            },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Outlined.Visibility
                        else Icons.Outlined.VisibilityOff,
                        contentDescription = null
                    )
                }
            }, isPasswordVisible = isPasswordVisible
        )
        Spacer(Modifier.height(8.dp))
        OutlinedText(
            value = confirmPassword,
            label = stringResource(R.string.confirm_password),
            onValueChange = { confirmPassword = it },
            placeholderText = "******",
            leadingIcon = {
                Icon(Icons.Outlined.Lock, "icon lock", tint = NeutralGray)
            },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Outlined.Visibility
                        else Icons.Outlined.VisibilityOff,
                        contentDescription = null
                    )
                }
            }, isPasswordVisible = isPasswordVisible
        )
        Spacer(Modifier.height(16.dp))
        ButtonPrimary(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            text = stringResource(R.string.register),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenColor,
                contentColor = Color.Black,
            )
        ) {
            onRegister()
        }

        Text(
            text = stringResource(R.string.back_to_login),
            fontSize = 14.sp,
            color = Gray,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .clickable {
                    onBackClick()
                }
        )
    }
}
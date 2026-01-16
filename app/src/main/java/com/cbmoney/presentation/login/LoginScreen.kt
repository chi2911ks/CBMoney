package com.cbmoney.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
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
import com.cbmoney.presentation.components.ButtonPrimary
import com.cbmoney.presentation.components.DividerWithText
import com.cbmoney.presentation.components.LanguageToggle
import com.cbmoney.presentation.components.OutlineButtonPrimary
import com.cbmoney.presentation.components.OutlinedText
import com.cbmoney.ui.theme.BrightBlue
import com.cbmoney.ui.theme.GreenColor
import com.cbmoney.ui.theme.NeutralGray
import com.cbmoney.utils.setAppLocale


@Composable
fun LoginScreen(onRegister: () -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                text = stringResource(R.string.login_h1),
                fontSize = 32.sp,
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.login_h3),
                fontSize = 16.sp,
                color = NeutralGray,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(32.dp))
            AuthForm(Modifier.fillMaxWidth()) {
                onRegister()
            }
            AuthProviders(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 25.dp)
            )
        }



    }
}


@Composable
fun AuthForm(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit = {},
    onRegister: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        OutlinedText(
            value = email,
            label = stringResource(R.string.email),
            onValueChange = { email = it },
            placeholderText = "name@example.com",
            leadingIcon = {
                Icon(Icons.Outlined.Email, "icon email", tint = NeutralGray)
            }

        )
        Spacer(Modifier.height(16.dp))
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
        Spacer(Modifier.height(16.dp))
        Text(
            modifier = Modifier.align(Alignment.End),
            text = stringResource(R.string.forgot_password),
            color = BrightBlue,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(16.dp))
        ButtonPrimary(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            text = stringResource(R.string.login),

            colors = ButtonDefaults.buttonColors(
                containerColor = GreenColor,
                contentColor = Color.Black,
            )
        ) {

        }
        Spacer(Modifier.height(8.dp))
        OutlineButtonPrimary(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            text = stringResource(R.string.register_new_account),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black,
            )
        ) {
            onRegister()
        }


    }
}

@Composable
fun AuthProviders(
    modifier: Modifier = Modifier,
    onLoginWithGoogle: () -> Unit = {},
    onLoginWithApple: () -> Unit = {}
) {
    Column(modifier = modifier) {
        DividerWithText(stringResource(R.string.continue_with))
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlineButtonPrimary(
                modifier = Modifier.size(width = 160.dp, height = 48.dp),
                text = "Google",
                iconInt = R.drawable.ic_google,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                )
            ) {
                onLoginWithGoogle()
            }
            OutlineButtonPrimary(
                modifier = Modifier.size(width = 160.dp, height = 48.dp),

                text = "Apple",
                iconInt = R.drawable.ic_apple,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                )
            ) {
                onLoginWithApple()
            }
        }
    }
}
package com.cbmoney.presentation.register

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.R
import com.cbmoney.data.mapper.toMessage
import com.cbmoney.presentation.components.ButtonPrimary
import com.cbmoney.presentation.components.LanguageToggle
import com.cbmoney.presentation.components.LottieView
import com.cbmoney.presentation.components.OutlinedText
import com.cbmoney.presentation.login.AuthProviders
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyColors.Neutral.NeutralGray
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.getLanguageCode
import com.cbmoney.utils.exts.handleOnSaveLanguage
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegisterScreen(
    navigateToHome: () -> Unit,
    onBackClick: () -> Unit,
    registerViewModel: RegisterViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState by registerViewModel.viewState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        registerViewModel.singleEvent.collect {
            when (it) {
                is RegisterEvent.NavigateToHome -> navigateToHome()
                is RegisterEvent.RegisterError -> snackBarHostState.showSnackbar(
                    it.authError.toMessage(
                        context
                    )
                )
            }
        }
    }

    RegisterScreenContent(
        uiState = uiState,
        snackBarHostState,
        context = context,
        processIntent = registerViewModel::processIntent,
        onBackClick = onBackClick
    )

}

@Composable
fun RegisterScreenContent(
    uiState: RegisterState,
    snackBarHostState: SnackbarHostState,
    context: Context,
    processIntent: (RegisterIntent) -> Unit,
    onBackClick: () -> Unit
) {
    var lang = context.getLanguageCode()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        LanguageToggle(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(horizontal = 16.dp),
            initialIsVN = lang == "vi",
        ) { isVN ->
            lang = if (isVN) "vi" else "en"
            context.handleOnSaveLanguage(lang)
        }
        Column(
            modifier = Modifier.padding(
                top = 55.dp,
                start = 16.dp,
                end = 16.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_finance),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(R.string.create_account),
                style = CBMoneyTypography.Headline.Large.ExtraBold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.signup_manage),
                color = CBMoneyColors.Neutral.NeutralGray,
                style = CBMoneyTypography.Body.Large.Regular
            )
            Spacer(Modifier.height(32.dp))
            SignUpForm(
                onBackClick = onBackClick,
                onRegister = {
                    processIntent(RegisterIntent.RegisterEmail(it.first(), it.last()))
                }
            )


            AuthProviders(
                modifier = Modifier.padding(bottom = 25.dp),
                onLoginWithGoogle = {
                    processIntent(RegisterIntent.GoogleRegister(context as Activity))
                },
                onLoginWithApple = {}
            )
        }
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = Spacing.lg)
        )
    }
    if (uiState.isLoading) {
        Box(
            modifier = Modifier
                .background(CBMoneyColors.Black.copy(alpha = 0.5f))
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LottieView(
                lottieResId = R.raw.anim_loading_white,
                modifier = Modifier.size(60.dp)
            )
        }
    }

}

@Composable
fun SignUpForm(
    onBackClick: () -> Unit,
    onRegister: (
        Set<String>
    ) -> Unit
) {
//    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth()) {
//        OutlinedText(
//            value = fullName,
//            label = stringResource(R.string.full_name),
//            onValueChange = { fullName = it },
//            placeholderText = "Alice Ninja",
//            leadingIcon = {
//                Icon(Icons.Default.AccountCircle, "icon user", tint = NeutralGray)
//            }
//
//        )
        Spacer(Modifier.height(8.dp))
        OutlinedText(
            value = email,
            label = stringResource(R.string.email),
            onValueChange = { email = it },
            placeholderText = "name@example.com",
            leadingIcon = {
                Icon(
                    Icons.Default.Email,
                    "icon email",
                    tint = CBMoneyColors.Neutral.NeutralGray)
            }

        )
        Spacer(Modifier.height(8.dp))
        PasswordInput(
            value = password,
            label = stringResource(R.string.password),
            onValueChange = { password = it },
            isPasswordVisible = isPasswordVisible,
            onVisibilityChange = { isPasswordVisible = it }
        )
        Spacer(Modifier.height(8.dp))
        PasswordInput(
            value = confirmPassword,
            label = stringResource(R.string.confirm_password),
            onValueChange = { confirmPassword = it },
            isPasswordVisible = isPasswordVisible,
            onVisibilityChange = { isPasswordVisible = it }
        )
        Spacer(Modifier.height(16.dp))
        ButtonPrimary(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            text = stringResource(R.string.register),
            onClick = {
                onRegister(
                    setOf(
                        email,
                        password
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = CBMoneyColors.Primary.Primary,
                contentColor = CBMoneyColors.Text.TextPrimary,
            )
        )

        Text(
            text = stringResource(R.string.back_to_login),
            fontSize = 14.sp,
            color = CBMoneyColors.Neutral.NeutralGray,
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

@Composable
fun PasswordInput(
    value: String,
    label: String,
    isPasswordVisible: Boolean,
    onValueChange: (String) -> Unit,
    onVisibilityChange: (Boolean) -> Unit
) {
    OutlinedText(
        value = value,
        label = label,
        onValueChange = { onValueChange(it) },
        placeholderText = "********",
        leadingIcon = {
            Icon(Icons.Outlined.Lock, "icon lock", tint = NeutralGray)
        },
        trailingIcon = {
            IconButton(onClick = { onVisibilityChange(!isPasswordVisible) }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Outlined.Visibility
                    else Icons.Outlined.VisibilityOff,
                    contentDescription = null
                )
            }
        }, isPasswordVisible = isPasswordVisible
    )
}

@Preview
@Composable
private fun RegisterScreenContentPreview() {
    RegisterScreenContent(
        uiState = RegisterState(),
        snackBarHostState = TODO(),
        context = TODO(),
        processIntent = TODO(),
        onBackClick = TODO()
    )
}
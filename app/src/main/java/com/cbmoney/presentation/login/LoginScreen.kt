package com.cbmoney.presentation.login

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.R
import com.cbmoney.data.mapper.toMessage
import com.cbmoney.presentation.components.ButtonPrimary
import com.cbmoney.presentation.components.DividerWithText
import com.cbmoney.presentation.components.LanguageToggle
import com.cbmoney.presentation.components.LottieView
import com.cbmoney.presentation.components.OutlineButtonPrimary
import com.cbmoney.presentation.components.OutlinedText
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyColors.Neutral.NeutralGray
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.getLanguageCode
import com.cbmoney.utils.exts.handleOnSaveLanguage
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    onRegister: () -> Unit,
    loginViewModel: LoginViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState by loginViewModel.viewState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        loginViewModel.singleEvent.collectLatest {
            when (it) {
                is LoginEvent.NavigateToHome -> navigateToHome()
                is LoginEvent.LoginError -> snackBarHostState.showSnackbar(
                    it.authError.toMessage(
                        context
                    )
                )
            }
        }

    }

    LoginScreenContent(
        onRegister = onRegister,
        uiState = uiState,
        snackBarHostState = snackBarHostState,
        context = context,
        processIntent = loginViewModel::processIntent
    )
}

@Composable
fun LoginScreenContent(
    onRegister: () -> Unit,
    uiState: LoginState,
    snackBarHostState: SnackbarHostState,
    context: Context,
    processIntent: (LoginIntent) -> Unit
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
            ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_finance),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(R.string.login_h1),
                style = CBMoneyTypography.Headline.Large.ExtraBold,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.login_h3),
                color = CBMoneyColors.Neutral.NeutralGray,
                style = CBMoneyTypography.Body.Large.Regular,
            )
            Spacer(Modifier.height(32.dp))
            AuthForm(
                onLogin = {
                    processIntent(LoginIntent.EmailLogin(it.first(), it.last()))
                },
                onRegister = onRegister
            )
            AuthProviders(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 25.dp),
                onLoginWithGoogle = {
                    processIntent(LoginIntent.GoogleLogin(context as Activity))
                },
                {}

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
                .background(Color.Black.copy(alpha = 0.5f))
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
fun AuthForm(
    modifier: Modifier = Modifier,
    onLogin: (Set<String>) -> Unit,
    onRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    Column(modifier = modifier.fillMaxWidth()) {
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
            color = CBMoneyColors.Blue,
            style = CBMoneyTypography.Title.Small.Bold
        )
        Spacer(Modifier.height(16.dp))
        ButtonPrimary(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                onLogin(
                    setOf(
                        email,
                        password
                    )
                )
            },
            text = stringResource(R.string.login),
            colors = ButtonDefaults.buttonColors(
                containerColor = CBMoneyColors.Primary.Primary,
                contentColor = CBMoneyColors.Text.TextPrimary,
            )
        )


        Spacer(Modifier.height(8.dp))
        OutlineButtonPrimary(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            text = stringResource(R.string.register_new_account),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = CBMoneyColors.White,
                contentColor = CBMoneyColors.Text.TextPrimary,
            )
        ) {
            onRegister()
        }


    }
}

@Composable
fun AuthProviders(
    modifier: Modifier = Modifier,
    onLoginWithGoogle: () -> Unit,
    onLoginWithApple: () -> Unit
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
package com.cbmoney.presentation.forgotpassword

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.R
import com.cbmoney.presentation.app.SnackbarManager
import com.cbmoney.presentation.app.UiMessage
import com.cbmoney.presentation.common.mapper.toMessage
import com.cbmoney.presentation.components.button.ButtonPrimary
import com.cbmoney.presentation.components.field.OutlinedText
import com.cbmoney.presentation.components.view.LottieView
import com.cbmoney.presentation.forgotpassword.contract.ForgotPasswordEvent
import com.cbmoney.presentation.forgotpassword.contract.ForgotPasswordIntent
import com.cbmoney.presentation.forgotpassword.contract.ForgotPasswordState
import com.cbmoney.presentation.forgotpassword.viewmodel.ForgotPasswordViewModel
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.utils.exts.rawClickable
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun ForgotPasswordScreen(
    onBackNavigation: () -> Unit,
    forgotPasswordViewModel: ForgotPasswordViewModel = koinViewModel(),
    snackbarManager: SnackbarManager = koinInject()
) {
    val context = LocalContext.current
    val uiState by forgotPasswordViewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        forgotPasswordViewModel.singleEvent.collectLatest { event ->
            when (event) {
                is ForgotPasswordEvent.ResetPasswordSuccess -> {
                    snackbarManager.show(UiMessage.Res(R.string.str_password_reset_sent))
                    onBackNavigation()
                }
                is ForgotPasswordEvent.ResetPasswordError -> {
                    snackbarManager.show(UiMessage.Text(event.authError.toMessage(context)))
                }
            }
        }
    }

    ForgotPasswordContent(
        uiState = uiState,
        onBackNavigation = onBackNavigation,
        processIntent = forgotPasswordViewModel::processIntent
    )
}

@Composable
fun ForgotPasswordContent(
    uiState: ForgotPasswordState,
    onBackNavigation: () -> Unit,
    processIntent: (ForgotPasswordIntent) -> Unit
) {
    var email by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .rawClickable { onBackNavigation() }
                )
                Text(
                    text = stringResource(R.string.str_forgot_password),
                    style = CBMoneyTypography.Title.Medium.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 48.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(CBMoneyColors.Primary.Primary.copy(alpha = 0.15f))
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LockReset,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = CBMoneyColors.Primary.Primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Title & Description
            Text(
                text = stringResource(R.string.str_forgot_password_title),
                style = CBMoneyTypography.Title.Medium.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.str_forgot_password_desc),
                style = CBMoneyTypography.Body.Medium.Regular,
                color = CBMoneyColors.Text.TextTertiary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email Input
            OutlinedText(
                value = email,
                label = stringResource(R.string.str_email),
                onValueChange = { email = it },
                placeholderText = "example@email.com",
                leadingIcon = {
                    Icon(Icons.Outlined.Email, "icon email", tint = CBMoneyColors.Neutral.NeutralGray)
                }
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(32.dp))
            
            // Bottom Buttons
            ButtonPrimary(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = {
                    processIntent(ForgotPasswordIntent.SendResetEmail(email))
                },
                text = stringResource(R.string.str_send_verification_code),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CBMoneyColors.Primary.Primary,
                    contentColor = CBMoneyColors.Text.TextPrimary,
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onBackNavigation,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.str_back_to_login),
                    style = CBMoneyTypography.Title.Small.Medium,
                    color = CBMoneyColors.Text.TextSecondary
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .pointerInput(Unit) {},
                contentAlignment = Alignment.Center
            ) {
                LottieView(
                    lottieResId = R.raw.anim_loading_white,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}

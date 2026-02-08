package com.cbmoney.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.R
import com.cbmoney.presentation.components.button.LanguageToggle
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.utils.exts.getLanguageCode
import com.cbmoney.utils.exts.handleOnSaveLanguage


@Composable
fun OnBoardingScreen(onContinueClicked: () -> Unit) {
    val context = LocalContext.current
    var lang = context.getLanguageCode()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CBMoneyColors.BackGround.BackgroundPrimary)
            .statusBarsPadding()
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
        OnboardingHeader(
            Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)
        )
        OnboardingGetStarted(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 25.dp)
        ) {
            onContinueClicked()
        }

    }
}

@Composable
fun OnboardingHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.img_pig),
            contentDescription = "image pig",
            modifier = Modifier
                .size(320.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.expense_management),
            style = CBMoneyTypography.Headline.Medium.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.smart_money_management),
            color = CBMoneyColors.Neutral.NeutralGray,
            style = CBMoneyTypography.Title.Small.Bold,
        )
    }
}

@Composable
fun OnboardingGetStarted(modifier: Modifier = Modifier, onContinueClicked: () -> Unit) {
    Column(
        modifier = modifier
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = CBMoneyColors.Black,
                containerColor = CBMoneyColors.Primary.Primary
            ),
            onClick = { onContinueClicked() })
        {
            Text(
                text = stringResource(R.string.onboarding_get_started),
                style = CBMoneyTypography.Body.Large.Medium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = CBMoneyColors.Black
            )
        }
        Spacer(Modifier.height(16.dp))

        val annotatedText = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = CBMoneyColors.Gray.Gray5,
                    fontStyle = CBMoneyTypography.Body.Medium.Medium.fontStyle,
                )
            ) {
                append(stringResource(R.string.already_account))
            }
            append(" ")
            pushLink(
                LinkAnnotation.Clickable(
                    tag = "LOGIN"
                ) {
                    onContinueClicked()
                }
            )

            withStyle(
                SpanStyle(
                    color = CBMoneyColors.Text.TextPrimary,

                    fontStyle = CBMoneyTypography.Title.Small.Bold.fontStyle
                )
            ) {
                append(stringResource(R.string.login))
            }

            pop()
        }

        Text(
            text = annotatedText,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

    }
}

@Preview
@Composable
private fun OnBoardingPreview() {
    OnBoardingScreen {  }
}

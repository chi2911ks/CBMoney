package com.cbmoney.presentation.onboarding

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cbmoney.R
import com.cbmoney.presentation.components.LanguageToggle
import com.cbmoney.ui.theme.GreenColor
import com.cbmoney.utils.setAppLocale

@Composable
fun OnBoardingScreen(onContinueClicked: () -> Unit) {
    val context = LocalContext.current
    context.setAppLocale("vi")
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
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.smart_money_management),
            color = Color.Gray,
            fontSize = 18.sp,
            style = MaterialTheme.typography.labelLarge
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
                contentColor = Color.Black,
                containerColor = GreenColor
            ),
            onClick = { onContinueClicked() })
        {
            Text(
                text = stringResource(R.string.onboarding_get_started),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.Black
            )
        }
        Spacer(Modifier.height(16.dp))

        val annotatedText = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                )
            ) {
                append(stringResource(R.string.already_account))
            }
            append(" ")
            pushLink(
                LinkAnnotation.Clickable(
                    tag = "LOGIN"
                ){
                    onContinueClicked()
                }
            )

            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(stringResource(R.string.login))
            }

            pop()
        }

        Text(
            text = annotatedText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

    }
}


package com.cbmoney.presentation.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.R
import com.cbmoney.presentation.theme.CBMoneyTypography
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    navigateToHome: () -> Unit,
    navigateToOnBoarding: () -> Unit,
    navigateToSignIn: () -> Unit,
    viewModel: SplashViewModel = koinViewModel()

) {
    LaunchedEffect(Unit) {
        viewModel.processIntent(SplashIntent.CheckLogin)
    }
    LaunchedEffect(Unit) {
        delay(1500)
        viewModel.singleEvent.collect { event ->
            when (event) {
                is SplashEvent.NavigateToMain -> navigateToHome()
                is SplashEvent.NavigateToOnBoarding -> navigateToOnBoarding()
                is SplashEvent.NavigateToSignIn -> navigateToSignIn()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_finance),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = stringResource(R.string.app_name),
            style = CBMoneyTypography.Headline.Large.ExtraBold.copy(
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.7f),
                    offset = Offset(2f, 2f),
                    blurRadius = 4f
                )
            ),
            color = Color.Black
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun SplashScreenView() {
    SplashScreen(
        navigateToHome = TODO(),
        navigateToOnBoarding = TODO(),
        navigateToSignIn = TODO(),
    )
}
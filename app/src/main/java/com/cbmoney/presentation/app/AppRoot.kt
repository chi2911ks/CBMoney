package com.cbmoney.presentation.app

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.cbmoney.presentation.navigation.NavRoutes
import com.cbmoney.presentation.theme.CBMoneyColors
import org.koin.compose.koinInject

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppRoot(
    snackbarManager: AppSnackbarManager = koinInject()
) {

    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        snackbarManager.messages.collect {
            when(it){
                is UiMessage.Res -> snackBarHostState
                is UiMessage.Text -> snackBarHostState.showSnackbar(it.text)
            }

        }
    }
    Scaffold (
        snackbarHost = { SnackbarHost(snackBarHostState) },
        modifier = Modifier.background(CBMoneyColors.BackGround.BackgroundPrimary)
    ){
        NavRoutes()
    }
}
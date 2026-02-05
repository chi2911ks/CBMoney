package com.cbmoney

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.cbmoney.presentation.app.AppRoot

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            setTheme(R.style.Theme_CBMoney)
        }
        setContent {
            AppRoot()
        }
    }
}


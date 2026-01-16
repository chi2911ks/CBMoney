package com.cbmoney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cbmoney.presentation.navigation.NavController
import com.cbmoney.ui.theme.CBMoneyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CBMoneyTheme {

                NavController()
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

}
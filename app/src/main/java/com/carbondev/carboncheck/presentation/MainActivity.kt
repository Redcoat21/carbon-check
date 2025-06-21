package com.carbondev.carboncheck.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.carbondev.carboncheck.presentation.navbar.MainScreen
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarbonCheckTheme {
                NavigationComponent()
//                MainScreen()
            }
        }
    }
}
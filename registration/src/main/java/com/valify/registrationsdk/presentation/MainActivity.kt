package com.valify.registrationsdk.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.valify.registrationsdk.presentation.navigation.NavigationHost
import com.valify.registrationsdk.presentation.theme.RegistrationSDKTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            RegistrationSDKTheme {
                NavigationHost(navController)
            }
        }
    }
}

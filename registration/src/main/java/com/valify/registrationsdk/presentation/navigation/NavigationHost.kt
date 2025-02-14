package com.valify.registrationsdk.presentation.navigation

import androidx.compose.runtime.Composable
 import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.valify.registrationsdk.presentation.ui.registration.RegistrationScreen

@Composable
fun NavigationHost(navController: NavHostController) {

    NavHost(navController = navController,startDestination = RouteRegistration){
        composable<RouteRegistration>{
            RegistrationScreen{

            }
        }
    }
}
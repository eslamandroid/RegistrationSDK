package com.valify.registrationsdk.presentation.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.valify.registrationsdk.presentation.ui.captureImage.CaptureImageScreen
import com.valify.registrationsdk.presentation.ui.registration.RegistrationScreen

@Composable
fun NavigationHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = RouteRegistration) {
        composable<RouteRegistration> {
            RegistrationScreen {
                navController.navigate(RouteCaptureImage) {
                    popUpTo(RouteRegistration) { inclusive = true }
                }
            }

        }
        composable<RouteCaptureImage> {
            val activity = LocalActivity.current
            CaptureImageScreen {
                activity?.finish()
            }

        }
    }
}
package com.valify.registrationsdk.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

val LocalAppDimens = compositionLocalOf {
    Compact
}

@Composable
fun ProvideAppUtils(
    appDimens: Dimens,
    content: @Composable () -> Unit,
) {
    val dimensions = remember { appDimens }
    CompositionLocalProvider(LocalAppDimens provides dimensions) {
        content()
    }
}

val ScreenOrientation
    @Composable
    get() = LocalConfiguration.current.orientation
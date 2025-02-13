package com.valify.registrationsdk.presentation.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val paddingSmall: Dp = 0.dp,
    val paddingMedium: Dp = 0.dp,
    val paddingLarge: Dp = 0.dp,
    val textSizeSmall: Dp = 0.dp,
    val textSizeMedium: Dp = 0.dp,
    val textSizeLarge: Dp = 0.dp,
    val heightSmall: Dp = 0.dp,
    val heightMedium: Dp = 0.dp,
    val heightLarge: Dp = 0.dp,
)

val CompactSmall = Dimens(
    paddingSmall = 6.dp,
    paddingMedium = 10.dp,
    paddingLarge = 14.dp,
    textSizeSmall = 12.dp,
    textSizeMedium = 14.dp,
    textSizeLarge = 16.dp,
    heightSmall = 35.dp,
    heightMedium = 45.dp,
    heightLarge = 55.dp
)



val Compact = Dimens(
    paddingSmall = 8.dp,
    paddingMedium = 12.dp,
    paddingLarge = 16.dp,
    textSizeSmall = 14.dp,
    textSizeMedium = 16.dp,
    textSizeLarge = 18.dp,
    heightSmall = 40.dp,
    heightMedium = 50.dp,
    heightLarge = 60.dp
)

val Medium = Dimens(
    paddingSmall = 12.dp,
    paddingMedium = 16.dp,
    paddingLarge = 24.dp,
    textSizeSmall = 16.dp,
    textSizeMedium = 18.dp,
    textSizeLarge = 20.dp,
    heightSmall = 50.dp,
    heightMedium = 60.dp,
    heightLarge = 70.dp
)


val Expanded = Dimens(
    paddingSmall = 16.dp,
    paddingMedium = 24.dp,
    paddingLarge = 32.dp,
    textSizeSmall = 18.dp,
    textSizeMedium = 20.dp,
    textSizeLarge = 22.dp,
    heightSmall = 60.dp,
    heightMedium = 70.dp,
    heightLarge = 80.dp
)
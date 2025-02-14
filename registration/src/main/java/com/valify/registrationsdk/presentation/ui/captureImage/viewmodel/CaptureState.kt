package com.valify.registrationsdk.presentation.ui.captureImage.viewmodel

import androidx.annotation.Keep
import com.valify.registrationsdk.domain.utils.AppError

@Keep
data class CaptureState(
    val success: Boolean = false,
    val appFailure: AppError? = null,
)


package com.valify.registrationsdk.presentation.ui.registration.viewmodel

import androidx.annotation.Keep
import com.valify.registrationsdk.domain.utils.AppError

@Keep
data class RegistrationState(
    val loading: Boolean = false,
    val success: Boolean = false,
    val appFailure: AppError? = null,
)


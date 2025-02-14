package com.valify.registrationsdk.presentation.ui.registration.viewmodel

import androidx.annotation.Keep
import com.valify.registrationsdk.domain.model.UserModel

@Keep
sealed class RegistrationIntent {
    data class SaveUserIntent(val input: UserModel) : RegistrationIntent()
    data object ResetState:RegistrationIntent()
}
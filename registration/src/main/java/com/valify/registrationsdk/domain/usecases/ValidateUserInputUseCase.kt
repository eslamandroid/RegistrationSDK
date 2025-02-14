package com.valify.registrationsdk.domain.usecases

import com.valify.registrationsdk.domain.model.UserModel
import com.valify.registrationsdk.domain.utils.AppError
import com.valify.registrationsdk.domain.utils.ResultSource
import com.valify.registrationsdk.domain.utils.ValidationType
import com.valify.registrationsdk.utils.isValidEmail
import com.valify.registrationsdk.utils.isValidPassword
import com.valify.registrationsdk.utils.isValidPhone
import javax.inject.Inject

class ValidateUserInputUseCase @Inject constructor() {

    operator fun invoke(user: UserModel): ResultSource<Boolean> {
        if (user.username.isBlank())
            return ResultSource.Error(AppError.ValidateError("username"))
        if (user.username.length <= 3)
            return ResultSource.Error(AppError.ValidateError("username", ValidationType.Invalid))

        if (user.email.isBlank())
            return ResultSource.Error(AppError.ValidateError("email"))
        if (!user.email.isValidEmail())
            return ResultSource.Error(AppError.ValidateError("email", ValidationType.Invalid))

        if (user.phone.isBlank())
            return ResultSource.Error(AppError.ValidateError("phone"))
        if (!user.phone.isValidPhone())
            return ResultSource.Error(AppError.ValidateError("phone", ValidationType.Invalid))

        if (user.password.isBlank())
            return ResultSource.Error(AppError.ValidateError("password"))
        if (!user.password.isValidPassword())
            return ResultSource.Error(AppError.ValidateError("password", ValidationType.Invalid))

        return ResultSource.Success(true)
    }


}
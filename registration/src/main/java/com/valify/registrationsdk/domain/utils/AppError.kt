package com.valify.registrationsdk.domain.utils


sealed class AppError {
    data object DatabaseError : AppError()

    data object UnknownError : AppError()

    data class ValidateError(val fieldKey: String, val type: ValidationType = ValidationType.Empty) : AppError()
}

enum class ValidationType {
    Empty,
    Invalid
}
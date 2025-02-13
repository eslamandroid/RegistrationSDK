package com.valify.registrationsdk.domain.utils

sealed class ResultSource<out T> {
    data class Success<out T>(val data: T) : ResultSource<T>()
    data class Error(val exception: AppError) : ResultSource<Nothing>()
}



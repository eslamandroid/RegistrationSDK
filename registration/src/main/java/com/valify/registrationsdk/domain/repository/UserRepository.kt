package com.valify.registrationsdk.domain.repository

import com.valify.registrationsdk.domain.model.UserModel
import com.valify.registrationsdk.domain.utils.ResultSource

interface UserRepository {
    suspend fun saveUser(user: UserModel): ResultSource<Boolean>
}
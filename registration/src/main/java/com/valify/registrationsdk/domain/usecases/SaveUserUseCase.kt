package com.valify.registrationsdk.domain.usecases

import com.valify.registrationsdk.domain.model.UserModel
import com.valify.registrationsdk.domain.repository.UserRepository
import com.valify.registrationsdk.domain.utils.ResultSource
import javax.inject.Inject

internal class SaveUserUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(user: UserModel): ResultSource<Boolean> = repository.saveUser(user)

}
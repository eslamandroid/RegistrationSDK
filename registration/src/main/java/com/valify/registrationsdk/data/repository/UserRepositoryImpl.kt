package com.valify.registrationsdk.data.repository

import android.database.sqlite.SQLiteException
import com.valify.registrationsdk.data.datasources.local.dao.UserDao
import com.valify.registrationsdk.data.datasources.local.entity.toEntity
import com.valify.registrationsdk.domain.model.UserModel
import com.valify.registrationsdk.domain.repository.UserRepository
import com.valify.registrationsdk.domain.utils.AppError
import com.valify.registrationsdk.domain.utils.ResultSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserRepository {
    override suspend fun saveUser(user: UserModel): ResultSource<Boolean> = withContext(dispatcher) {
        kotlin.runCatching {
            userDao.insertUser(user.toEntity())
            ResultSource.Success(true)
        }.getOrElse {
            if (it is SQLiteException) {
                ResultSource.Error(AppError.DatabaseError)
            } else {
                ResultSource.Error(AppError.UnknownError)
            }
        }
    }
}
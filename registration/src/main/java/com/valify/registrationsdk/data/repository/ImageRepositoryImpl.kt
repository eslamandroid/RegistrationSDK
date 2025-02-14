package com.valify.registrationsdk.data.repository

import android.database.sqlite.SQLiteException
import com.valify.registrationsdk.data.datasources.local.dao.ImageDao
import com.valify.registrationsdk.data.datasources.local.entity.ImageEntity
import com.valify.registrationsdk.domain.repository.ImageRepository
import com.valify.registrationsdk.domain.utils.AppError
import com.valify.registrationsdk.domain.utils.ResultSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageDao: ImageDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ImageRepository {
    override suspend fun saveCapturedImage(image: String): ResultSource<Boolean> = withContext(dispatcher) {
        kotlin.runCatching {
            imageDao.insertImage(ImageEntity(imageData = image))
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
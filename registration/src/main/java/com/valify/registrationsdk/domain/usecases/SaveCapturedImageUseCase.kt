package com.valify.registrationsdk.domain.usecases

import android.graphics.Bitmap
import com.valify.registrationsdk.domain.repository.ImageRepository
import com.valify.registrationsdk.domain.utils.ResultSource
import com.valify.registrationsdk.utils.bitmapToBase64
import javax.inject.Inject

internal class SaveCapturedImageUseCase @Inject constructor(private val repository: ImageRepository) {

    suspend operator fun invoke(image: Bitmap): ResultSource<Boolean> =
        repository.saveCapturedImage(image.bitmapToBase64())

}
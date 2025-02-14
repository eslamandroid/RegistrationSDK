package com.valify.registrationsdk.domain.repository

import com.valify.registrationsdk.domain.utils.ResultSource

interface ImageRepository {

    suspend fun saveCapturedImage(image:String):ResultSource<Boolean>

}
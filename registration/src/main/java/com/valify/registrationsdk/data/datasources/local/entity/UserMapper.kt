package com.valify.registrationsdk.data.datasources.local.entity

import com.valify.registrationsdk.domain.model.UserModel


fun UserModel.toEntity(): UserEntity = UserEntity(
    username = username,
    phone = phone,
    email = email,
    password = password,
)
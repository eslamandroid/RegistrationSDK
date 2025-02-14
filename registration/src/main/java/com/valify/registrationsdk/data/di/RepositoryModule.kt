package com.valify.registrationsdk.data.di

import com.valify.registrationsdk.data.datasources.local.dao.ImageDao
import com.valify.registrationsdk.data.datasources.local.dao.UserDao
import com.valify.registrationsdk.data.repository.ImageRepositoryImpl
import com.valify.registrationsdk.data.repository.UserRepositoryImpl
import com.valify.registrationsdk.domain.repository.ImageRepository
import com.valify.registrationsdk.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(
        userDao: UserDao,
    ): UserRepository {
        return UserRepositoryImpl(userDao)
    }

     @Provides
    @Singleton
    fun provideImageRepository(
        imageDao: ImageDao,
    ): ImageRepository {
        return ImageRepositoryImpl(imageDao)
    }




}
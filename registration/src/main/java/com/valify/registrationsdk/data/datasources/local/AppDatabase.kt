package com.valify.registrationsdk.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.valify.registrationsdk.data.datasources.local.dao.UserDao
import com.valify.registrationsdk.data.datasources.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
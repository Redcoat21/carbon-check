package com.carbondev.carboncheck.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carbondev.carboncheck.data.local.dao.UserDao
import com.carbondev.carboncheck.data.local.model.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
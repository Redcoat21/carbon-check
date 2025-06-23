package com.carbondev.carboncheck.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.carbondev.carboncheck.data.local.dao.ActivityDao
import com.carbondev.carboncheck.data.local.dao.UserDao
import com.carbondev.carboncheck.data.local.entity.ActivityEntity
import com.carbondev.carboncheck.data.local.model.UserEntity

@Database(
    entities =
        [
            UserEntity::class,
            ActivityEntity::class
        ],
    version = 2
)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun activityDao(): ActivityDao
}
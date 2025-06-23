package com.carbondev.carboncheck.di

import android.content.Context
import androidx.room.Room
import com.carbondev.carboncheck.data.local.AppDatabase
import com.carbondev.carboncheck.data.local.dao.ActivityDao
import com.carbondev.carboncheck.data.local.datasource.UserLocalDataSource
import com.carbondev.carboncheck.data.local.dao.UserDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "carbon_check_db"
        )
        .fallbackToDestructiveMigration(false)
        .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideActivityDao(appDatabase: AppDatabase): ActivityDao {
        return appDatabase.activityDao()
    }
}
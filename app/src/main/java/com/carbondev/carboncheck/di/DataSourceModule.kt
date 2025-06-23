package com.carbondev.carboncheck.di

import com.carbondev.carboncheck.data.local.ActivityLocalDataSource
import com.carbondev.carboncheck.data.local.ActivityLocalDataSourceImplementation
import com.carbondev.carboncheck.data.local.datasource.UserLocalDataSource
import com.carbondev.carboncheck.data.local.datasource.UserLocalDataSourceImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindUserLocalDataSource(
        userLocalDataSourceImpl: UserLocalDataSourceImplementation
    ): UserLocalDataSource

    @Binds
    @Singleton
    abstract fun bindActivityLocalDataSource(
        activityLocalDataSourceImpl: ActivityLocalDataSourceImplementation
    ): ActivityLocalDataSource
}
package com.carbondev.carboncheck.di

import com.carbondev.carboncheck.data.error.ErrorHandlerImplementation
import com.carbondev.carboncheck.domain.error.ErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dependency injection module for providing error-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object ErrorModule {
    /**
     * Provides a singleton instance of ErrorHandler.
     */
    @Provides
    fun provideErrorHandler(impl: ErrorHandlerImplementation): ErrorHandler {
        return impl
    }
}
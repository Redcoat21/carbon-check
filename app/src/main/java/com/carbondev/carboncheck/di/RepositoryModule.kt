package com.carbondev.carboncheck.di

import com.carbondev.carboncheck.data.repository.UserRepositoryRemoteImplementation
import com.carbondev.carboncheck.data.repository.VendorRepositoryRemoteImplementation
import com.carbondev.carboncheck.data.repository.VoucherRepositoryImplementation
import com.carbondev.carboncheck.domain.repository.UserRepository
import com.carbondev.carboncheck.domain.repository.VendorRepository
import com.carbondev.carboncheck.domain.repository.VoucherRepository
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
    fun provideUserRepository(impl: UserRepositoryRemoteImplementation): UserRepository {
        return impl
    }

    @Provides
    @Singleton
    fun provideVendorRepository(impl: VendorRepositoryRemoteImplementation): VendorRepository {
        return impl
    }

    @Provides
    @Singleton
    fun provideVoucherRepository(impl: VoucherRepositoryImplementation): VoucherRepository {
        return impl
    }
}
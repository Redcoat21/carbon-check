package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.remote.supabase.VendorRemoteDataSource
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.model.Vendor
import com.carbondev.carboncheck.domain.repository.VendorRepository
import timber.log.Timber
import javax.inject.Inject

class VendorRepositoryRemoteImplementation @Inject constructor(
    private val remote: VendorRemoteDataSource,
    private val errorHandler: ErrorHandler
) : VendorRepository {
    override suspend fun getVendor(id: String): Result<Vendor?> {
        return runCatching { remote.getVendor(id) }.fold(onSuccess = {
            Result.Success(it.toDomainModel())
        }, onFailure = {
            Timber.e("Error fetching vendor $id: ${it.message}")
            Result.Error(type = errorHandler.mapToDomainError(it), exception = it)
        })
    }
}
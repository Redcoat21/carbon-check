package com.carbondev.carboncheck.data.remote.repository

import com.carbondev.carboncheck.data.remote.supabase.VendorRemoteDataSource
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.error.ErrorHandler
import com.carbondev.carboncheck.domain.model.Vendor
import com.carbondev.carboncheck.domain.repository.VendorRepository
import timber.log.Timber
import javax.inject.Inject

class VendorRepositoryRemoteImplementation @Inject constructor(private val remote: VendorRemoteDataSource, private val errorHandler: ErrorHandler) : VendorRepository {
    override suspend fun getVendor(id: String): Result<Vendor?> {
        return try {
            remote.getVendor(id)?.let { vendor ->
                Result.Success(vendor.toDomainModel())
            } ?: run {
                Timber.w("Vendor with id $id not found")
                Result.Error(message = "Vendor not found", type = ErrorType.NOT_FOUND_ERROR)
            }

        } catch (e: Exception) {
            Timber.e("Error fetching vendor $id: ${e.message}")
            Result.Error(type = errorHandler.mapToDomainError(e), exception = e)
        }
    }
}
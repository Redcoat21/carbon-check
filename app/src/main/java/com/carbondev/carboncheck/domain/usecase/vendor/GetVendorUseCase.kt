package com.carbondev.carboncheck.domain.usecase.vendor

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Vendor
import com.carbondev.carboncheck.domain.repository.VendorRepository
import timber.log.Timber
import javax.inject.Inject

class GetVendorUseCase @Inject constructor(private val repository: VendorRepository) {
    suspend operator fun invoke(id: String): Result<Vendor?> {
        if (id.isEmpty() || id.isBlank()) {
            Timber.w("Invalid vendor ID: $id")
            return Result.Error(
                type = ErrorType.VALIDATION_ERROR,
                message = "Vendor ID cannot be empty or blank"
            )
        }

        return repository.getVendor(id)
    }
}
package com.carbondev.carboncheck.domain.repository

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Vendor

/**
 * Repository interface to manage vendor data.
 */
interface VendorRepository {
    suspend fun getVendor(id: String): Result<Vendor?>
}
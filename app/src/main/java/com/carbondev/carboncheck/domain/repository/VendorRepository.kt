package com.carbondev.carboncheck.domain.repository

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Vendor

/**
 * Repository interface to manage vendor data.
 */
interface VendorRepository {
    /**
     * Fetches a list of vendors.
     * @return [Result] containing a list of vendors or an error.
     */
    suspend fun getVendor(id: String): Result<Vendor?>
}
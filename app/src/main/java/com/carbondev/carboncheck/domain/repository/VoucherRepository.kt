package com.carbondev.carboncheck.domain.repository

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Voucher
import com.carbondev.carboncheck.domain.model.VoucherIdentifier

/**
 * Repository interface for managing vouchers.
 */
interface VoucherRepository {
    /**
     * Get a list of vouchers.
     * @param identifier Optional identifier to filter vouchers. See [VoucherIdentifier]
     * @return [Result] containing a list of vouchers or an error.
     */
    suspend fun getVouchers(identifier: VoucherIdentifier? = null): Result<List<Voucher>>

    /**
     * Get a specific voucher by its ID.
     * @param id The unique identifier of the voucher.
     * @return [Result] containing the voucher or an error.
     */
    suspend fun getVoucher(id: String): Result<Voucher>
}
package com.carbondev.carboncheck.domain.repository

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Voucher
import com.carbondev.carboncheck.domain.model.VoucherIdentifier

interface VoucherRepository {
    suspend fun getVouchers(identifier: VoucherIdentifier? = null): Result<List<Voucher>>
    suspend fun getVoucher(id: String): Result<Voucher>
}
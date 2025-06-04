package com.carbondev.carboncheck.domain.usecase.vendor

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Voucher
import com.carbondev.carboncheck.domain.model.VoucherIdentifier
import com.carbondev.carboncheck.domain.repository.VoucherRepository
import javax.inject.Inject

class GetVendorVouchersUseCase @Inject constructor(private val repository: VoucherRepository) {
    suspend operator fun invoke(vendorId: String): Result<List<Voucher>> {
        if (vendorId.isBlank()) {
            return Result.Error(
                type = ErrorType.VALIDATION_ERROR,
                exception = IllegalArgumentException("Vendor ID cannot be blank")
            )
        }
        return repository.getVouchers(VoucherIdentifier.Vendor(vendorId))
    }
}
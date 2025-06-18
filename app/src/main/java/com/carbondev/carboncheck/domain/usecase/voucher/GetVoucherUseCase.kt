package com.carbondev.carboncheck.domain.usecase.voucher

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Voucher
import com.carbondev.carboncheck.domain.repository.VoucherRepository
import javax.inject.Inject

class GetVoucherUseCase @Inject constructor(private val voucherRepository: VoucherRepository) {
    suspend operator fun invoke(id: String): Result<Voucher> {
        if(id.isBlank()) {
            return Result.Error(type = ErrorType.VALIDATION_ERROR, exception = IllegalArgumentException("Voucher ID cannot be blank"))
        }
        return voucherRepository.getVoucher(id)
    }
}
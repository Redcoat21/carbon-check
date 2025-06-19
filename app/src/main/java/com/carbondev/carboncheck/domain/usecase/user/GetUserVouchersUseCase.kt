package com.carbondev.carboncheck.domain.usecase.user

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Voucher
import com.carbondev.carboncheck.domain.model.VoucherIdentifier
import com.carbondev.carboncheck.domain.repository.VoucherRepository
import javax.inject.Inject

class GetUserVouchersUseCase @Inject constructor(private val voucherRepository: VoucherRepository) {
    suspend operator fun invoke(userId: String): Result<List<Voucher>> {
        return voucherRepository.getVouchers(VoucherIdentifier.User(userId))
    }
}
package com.carbondev.carboncheck.domain.usecase.voucher

import com.carbondev.carboncheck.domain.repository.VoucherRepository
import javax.inject.Inject

class GetVouchersUseCase @Inject constructor(private val voucherRepository: VoucherRepository) {
    suspend operator fun invoke() = voucherRepository.getVouchers()
//    TODO: fixing the vendor parsing problem
}
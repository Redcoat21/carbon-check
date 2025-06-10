package com.carbondev.carboncheck.data.remote.repository

import com.carbondev.carboncheck.data.remote.model.NetworkVoucher
import com.carbondev.carboncheck.data.remote.supabase.VoucherRemoteDataSource
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.model.Voucher
import com.carbondev.carboncheck.domain.model.VoucherIdentifier
import com.carbondev.carboncheck.domain.repository.VoucherRepository
import timber.log.Timber
import javax.inject.Inject

class VoucherRepositoryImplementation @Inject constructor(
    private val remote: VoucherRemoteDataSource,
    private val errorHandler: ErrorHandler
) : VoucherRepository {
    override suspend fun getVoucher(id: String): Result<Voucher> {
        return runCatching { remote.getVoucher(id) }.fold(onSuccess = {
            Result.Success(it.toDomainModel())
        }, onFailure = {
            Timber.w("Error fetching voucher with id $id: ${it.message}")
            Result.Error(type = errorHandler.mapToDomainError(it), exception = it)
        })
    }

    override suspend fun getVouchers(identifier: VoucherIdentifier?): Result<List<Voucher>> {
        return runCatching { fetchVouchersByIdentifier(identifier) }.fold(onSuccess = {
            Result.Success(it.map { it.toDomainModel() })
        }, onFailure = {
            Timber.w("Error fetching vouchers for identifier $identifier: ${it.message}")
            Result.Error(type = errorHandler.mapToDomainError(it), exception = it)
        })
    }

    private suspend fun fetchVouchersByIdentifier(identifier: VoucherIdentifier?): List<NetworkVoucher> {
        return when (identifier) {
            null -> remote.getVouchers()
            is VoucherIdentifier.User -> emptyList()
            is VoucherIdentifier.Vendor -> remote.getVouchersByVendor(identifier.vendorId)
        }
    }
}
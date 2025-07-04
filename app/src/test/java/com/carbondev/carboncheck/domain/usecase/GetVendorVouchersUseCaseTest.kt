package com.carbondev.carboncheck.domain.usecase

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Voucher
import com.carbondev.carboncheck.domain.model.VoucherIdentifier
import com.carbondev.carboncheck.domain.repository.VoucherRepository
import com.carbondev.carboncheck.domain.usecase.vendor.GetVendorVouchersUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetVendorVouchersUseCaseTest {
    @MockK
    private lateinit var mockRepository: VoucherRepository
    private lateinit var getVendorVouchersUseCase: GetVendorVouchersUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getVendorVouchersUseCase = GetVendorVouchersUseCase(mockRepository)
    }

    @Test
    fun `invoke should return all vouchers that belong to the vendor`() = runTest {
        // Arrange
        val vendorId = "vendor123"
        val mockVouchers = listOf<Voucher>(
            mockk(relaxed = true),
            mockk(relaxed = true)
        )
        coEvery { mockRepository.getVouchers(VoucherIdentifier.Vendor(vendorId)) } returns Result.Success(mockVouchers)

        // Act
        val result = getVendorVouchersUseCase(vendorId)

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(mockVouchers, (result as Result.Success).data)
        coVerify(exactly = 1) { mockRepository.getVouchers(VoucherIdentifier.Vendor(vendorId)) }
    }

    @Test
    fun `invoke should return empty list when vendor has no vouchers`() = runTest {
        // Arrange
        val vendorId = "user123"
        val emptyList = emptyList<Voucher>()
        coEvery { mockRepository.getVouchers(VoucherIdentifier.Vendor(vendorId)) } returns Result.Success(emptyList)

        // Act
        val result = getVendorVouchersUseCase(vendorId)

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(emptyList, (result as Result.Success).data)
        assertTrue(result.data.isEmpty())
    }

    @Test
    fun `invoke should propagate errors from repository`() = runTest {
        // Arrange
        val vendorId = "vendor123"
        val errorMessage = "Failed to fetch vouchers"
        coEvery { mockRepository.getVouchers(VoucherIdentifier.Vendor(vendorId)) } returns
                Result.Error(message = errorMessage, type = ErrorType.NETWORK_ERROR)

        // Act
        val result = getVendorVouchersUseCase(vendorId)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.NETWORK_ERROR, (result as Result.Error).type)
        assertEquals(errorMessage, result.message)
    }
}
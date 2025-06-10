package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.remote.model.NetworkVoucher
import com.carbondev.carboncheck.data.remote.repository.VoucherRepositoryImplementation
import com.carbondev.carboncheck.data.remote.supabase.VoucherRemoteDataSource
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.model.Voucher
import com.carbondev.carboncheck.domain.repository.VoucherRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue

class VoucherRepositoryTest {
    @MockK private lateinit var mockRemoteDataSource: VoucherRemoteDataSource
    @MockK private lateinit var mockErrorHandler: ErrorHandler
    private lateinit var voucherRepository: VoucherRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        voucherRepository = VoucherRepositoryImplementation(remote = mockRemoteDataSource, errorHandler = mockErrorHandler)
    }

    @Test
    fun `getVoucher should return a valid voucher`() = runTest {
        // Arrange
        val expectedVoucherId = "12345"
        val mockNetworkVoucher = mockk<NetworkVoucher>()
        val expectedVoucher = mockk<Voucher>()
        every { mockNetworkVoucher.toDomainModel() } returns expectedVoucher
        coEvery { mockRemoteDataSource.getVoucher(expectedVoucherId) } returns mockNetworkVoucher
        // Act
        val result = voucherRepository.getVoucher(expectedVoucherId)
        // Assert
        assertTrue(result is Result.Success)
        assertEquals(expectedVoucher, (result as Result.Success).data)
    }

    @Test
    fun `getVoucher should return a not found error when voucher is not found`() = runTest {
        // Arrange
        val expectedVoucherId = "12345"
        val expectedException = NoSuchElementException("Voucher not found")
        every { mockErrorHandler.mapToDomainError(expectedException) } returns ErrorType.NOT_FOUND_ERROR
        coEvery { mockRemoteDataSource.getVoucher(expectedVoucherId) } throws expectedException

        // Act
        val result = voucherRepository.getVoucher(expectedVoucherId)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.NOT_FOUND_ERROR, (result as Result.Error).type)
    }

    @Test
    fun `getVouchers should return a list of voucher when no identifier is given`() = runTest {
        // Arrange
        val expectedVoucher1 = mockk<Voucher>()
        val expectedVoucher2 = mockk<Voucher>()
        val expectedVouchers = listOf(expectedVoucher1, expectedVoucher2)

        // Create network vouchers and set up explicit mocking
        val mockNetworkVoucher1 = mockk<NetworkVoucher>()
        val mockNetworkVoucher2 = mockk<NetworkVoucher>()
        val mockNetworkVouchers = listOf(mockNetworkVoucher1, mockNetworkVoucher2)

        // Configure each mock individually
        every { mockNetworkVoucher1.toDomainModel() } returns expectedVoucher1
        every { mockNetworkVoucher2.toDomainModel() } returns expectedVoucher2

        coEvery { mockRemoteDataSource.getVouchers() } returns mockNetworkVouchers

        // Act
        val result = voucherRepository.getVouchers()

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(expectedVouchers, (result as Result.Success).data)
    }

    @Test
    fun `getVouchers shouldn't throw an error when no vouchers are given`() = runTest {
        // Arrange
        val expectedNetworkVouchers = emptyList<NetworkVoucher>()
        coEvery { mockRemoteDataSource.getVouchers() } returns expectedNetworkVouchers
        // Act
        val result = voucherRepository.getVouchers()
        // Assert
        assertTrue(result is Result.Success)
        assertTrue((result as Result.Success).data.isEmpty())
    }
}


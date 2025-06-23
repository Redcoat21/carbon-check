package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.remote.model.NetworkUsersVouchers
import com.carbondev.carboncheck.data.remote.model.NetworkVoucher
import com.carbondev.carboncheck.data.remote.supabase.VoucherRemoteDataSource
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.model.Voucher
import com.carbondev.carboncheck.domain.model.VoucherIdentifier
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
    fun `getVouchers should return vouchers when user identifier is given`() = runTest {
        // Arrange
        val userId = "user123"
        val userIdentifier = VoucherIdentifier.User(userId)

        // Create mock vouchers
        val expectedVoucher1 = mockk<Voucher>()
        val expectedVoucher2 = mockk<Voucher>()
        val expectedVouchers = listOf(expectedVoucher1, expectedVoucher2)

        // Create mock network vouchers
        val mockNetworkVoucher1 = mockk<NetworkVoucher>()
        val mockNetworkVoucher2 = mockk<NetworkVoucher>()

        // Create mock user-voucher relationships
        val mockUserVoucher1 = mockk<NetworkUsersVouchers> {
            every { voucher } returns mockNetworkVoucher1
        }
        val mockUserVoucher2 = mockk<NetworkUsersVouchers> {
            every { voucher } returns mockNetworkVoucher2
        }
        val mockUserVouchers = listOf(mockUserVoucher1, mockUserVoucher2)

        // Configure mocks
        every { mockNetworkVoucher1.toDomainModel() } returns expectedVoucher1
        every { mockNetworkVoucher2.toDomainModel() } returns expectedVoucher2
        coEvery { mockRemoteDataSource.getVouchersByUser(userId) } returns mockUserVouchers

        // Act
        val result = voucherRepository.getVouchers(userIdentifier)

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(expectedVouchers, (result as Result.Success).data)
    }

    @Test
    fun `getVouchers should return vouchers when vendor identifier is given`() = runTest {
        // Arrange
        val vendorId = "vendor456"
        val vendorIdentifier = VoucherIdentifier.Vendor(vendorId)

        // Create mock vouchers
        val expectedVoucher1 = mockk<Voucher>()
        val expectedVoucher2 = mockk<Voucher>()
        val expectedVouchers = listOf(expectedVoucher1, expectedVoucher2)

        // Create mock network vouchers
        val mockNetworkVoucher1 = mockk<NetworkVoucher>()
        val mockNetworkVoucher2 = mockk<NetworkVoucher>()
        val mockNetworkVouchers = listOf(mockNetworkVoucher1, mockNetworkVoucher2)

        // Configure mocks
        every { mockNetworkVoucher1.toDomainModel() } returns expectedVoucher1
        every { mockNetworkVoucher2.toDomainModel() } returns expectedVoucher2
        coEvery { mockRemoteDataSource.getVouchersByVendor(vendorId) } returns mockNetworkVouchers

        // Act
        val result = voucherRepository.getVouchers(vendorIdentifier)

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(expectedVouchers, (result as Result.Success).data)
    }

    @Test
    fun `getVouchers should handle error when user identifier fetch fails`() = runTest {
        // Arrange
        val userId = "invalidUser"
        val userIdentifier = VoucherIdentifier.User(userId)
        val expectedException = RuntimeException("Network error")

        coEvery { mockRemoteDataSource.getVouchersByUser(userId) } throws expectedException
        every { mockErrorHandler.mapToDomainError(expectedException) } returns ErrorType.NETWORK_ERROR

        // Act
        val result = voucherRepository.getVouchers(userIdentifier)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.NETWORK_ERROR, (result as Result.Error).type)
    }

    @Test
    fun `getVouchers should handle error when vendor identifier fetch fails`() = runTest {
        // Arrange
        val vendorId = "invalidVendor"
        val vendorIdentifier = VoucherIdentifier.Vendor(vendorId)
        val expectedException = RuntimeException("Database error")

        coEvery { mockRemoteDataSource.getVouchersByVendor(vendorId) } throws expectedException
        every { mockErrorHandler.mapToDomainError(expectedException) } returns ErrorType.DATABASE_ERROR

        // Act
        val result = voucherRepository.getVouchers(vendorIdentifier)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.DATABASE_ERROR, (result as Result.Error).type)
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

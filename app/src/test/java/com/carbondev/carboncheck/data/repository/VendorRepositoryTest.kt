package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.remote.model.NetworkVendor
import com.carbondev.carboncheck.data.remote.repository.VendorRepositoryRemoteImplementation
import com.carbondev.carboncheck.data.remote.supabase.VendorRemoteDataSource
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.error.ErrorHandler
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Vendor
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class VendorRepositoryTest {
    private lateinit var vendorRepository: VendorRepositoryRemoteImplementation
    private lateinit var mockRemoteDataSource: VendorRemoteDataSource
    private lateinit var mockErrorHandler: ErrorHandler

    @Before
    fun setUp() {
        mockRemoteDataSource = mockk<VendorRemoteDataSource>()
        mockErrorHandler = mockk<ErrorHandler>()
        vendorRepository = VendorRepositoryRemoteImplementation(mockRemoteDataSource, mockErrorHandler)
    }

    @Test
    fun `getVendor should return a valid profile`() = runTest {
        // Arrange
        val expectedVendorId = "12345"
        val mockNetworkVendor = mockk<NetworkVendor>()
        val expectedVendor = mockk<Vendor>()

        every { mockNetworkVendor.toDomainModel() } returns expectedVendor

        coEvery {
            mockRemoteDataSource.getVendor(expectedVendorId)
        } returns mockNetworkVendor

        // Act
        val result = vendorRepository.getVendor(expectedVendorId)

        // Assert
        assert(result is Result.Success)
        assert((result as Result.Success).data == expectedVendor)
    }

    @Test
    fun `getVendor should return a not found error when vendor is not found`() = runTest {
        // Arrange
        val expectedVendorId = "12345"

        coEvery {
            mockRemoteDataSource.getVendor(expectedVendorId)
        } returns null

        // Act
        val result = vendorRepository.getVendor(expectedVendorId)

        // Assert
        assert(result is Result.Error)
        assert((result as Result.Error).type == ErrorType.NOT_FOUND_ERROR)
    }
}
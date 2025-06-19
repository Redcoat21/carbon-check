package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.remote.model.NetworkVendor
import com.carbondev.carboncheck.data.remote.supabase.VendorRemoteDataSource
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Vendor
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class VendorRepositoryTest {
    @MockK private lateinit var mockRemoteDataSource: VendorRemoteDataSource
    @MockK private lateinit var mockErrorHandler: ErrorHandler
    private lateinit var vendorRepository: VendorRepositoryRemoteImplementation

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        vendorRepository = VendorRepositoryRemoteImplementation(mockRemoteDataSource, mockErrorHandler)
    }

    @Test
    fun `getVendor should return a valid vendor`() = runTest {
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
        assertTrue(result is Result.Success)
        assertEquals((result as Result.Success).data, expectedVendor)
    }

    @Test
    fun `getVendor should return a not found error when vendor is not found`() = runTest {
        // Arrange
        val expectedVendorId = "12345"
        val expectedException = NoSuchElementException("Vendor not found")

        every { mockErrorHandler.mapToDomainError(expectedException) } returns ErrorType.NOT_FOUND_ERROR
        coEvery {
            mockRemoteDataSource.getVendor(expectedVendorId)
        } throws expectedException

        // Act
        val result = vendorRepository.getVendor(expectedVendorId)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals((result as Result.Error).type, ErrorType.NOT_FOUND_ERROR)
    }
}
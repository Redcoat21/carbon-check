package com.carbondev.carboncheck.domain.usecase

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Vendor
import com.carbondev.carboncheck.domain.repository.VendorRepository
import com.carbondev.carboncheck.domain.usecase.vendor.GetVendorUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetVendorUseCaseTest {
    private lateinit var mockVendorRepository: VendorRepository

    @Before
    fun setUp() {
        mockVendorRepository = mockk<VendorRepository>()
    }

    @Test
    fun `invoke should return a vendor when given valid id`() = runTest {
        // Arrange
        val validId = "12345"
        val useCase = GetVendorUseCase(mockVendorRepository)
        val mockVendor = mockk<Vendor>()

        coEvery {
            mockVendorRepository.getVendor(validId)
        } returns Result.Success<Vendor>(data = mockVendor)

        // Act
        val result = useCase.invoke(validId)
        // Assert
        assertTrue(result is Result.Success)
        assertEquals((result as Result.Success).data, mockVendor)

    }

    @Test
    fun `invoke should return validation error when given invalid id`() = runTest {
        // Arrange
        val invalidId = ""
        val useCase = GetVendorUseCase(mockVendorRepository)
        coEvery {
            mockVendorRepository.getVendor(invalidId)
        } returns mockk()

        // Act
        val result = useCase.invoke(invalidId)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals((result as Result.Error).type, ErrorType.VALIDATION_ERROR)
    }

}
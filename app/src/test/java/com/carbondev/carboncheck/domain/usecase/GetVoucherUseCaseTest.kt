package com.carbondev.carboncheck.domain.usecase

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Voucher
import com.carbondev.carboncheck.domain.repository.VoucherRepository
import com.carbondev.carboncheck.domain.usecase.voucher.GetVoucherUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue

class GetVoucherUseCaseTest {
    @MockK
    private lateinit var mockRepository: VoucherRepository
    private lateinit var getVoucherUseCase: GetVoucherUseCase


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getVoucherUseCase = GetVoucherUseCase(mockRepository)
    }

    @Test
    fun `invoke should return voucher when ID is valid`() = runTest {
        // Arrange
        val voucherId = "validVoucherId"
        val mockVoucher = mockk<Voucher>(relaxed = true)
        coEvery { mockRepository.getVoucher(voucherId) } returns Result.Success(mockVoucher)

        // Act
        val result = getVoucherUseCase(voucherId)

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(mockVoucher, (result as Result.Success).data)
        coVerify(exactly = 1) { mockRepository.getVoucher(voucherId) }
    }

    @Test
    fun `invoke should return error when ID is blank`() = runTest {
        // Arrange
        val voucherId = ""

        // Act
        val result = getVoucherUseCase(voucherId)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.VALIDATION_ERROR, (result as Result.Error).type)
        assertTrue(result.exception is IllegalArgumentException)
        coVerify(exactly = 0) { mockRepository.getVoucher(any()) }
    }

    @Test
    fun `invoke should propagate repository error`() = runTest {
        // Arrange
        val voucherId = "errorVoucherId"
        val mockException = Exception("Repository error")
        coEvery { mockRepository.getVoucher(voucherId) } returns Result.Error(ErrorType.UNKNOWN_ERROR, exception = mockException)

        // Act
        val result = getVoucherUseCase(voucherId)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.UNKNOWN_ERROR, (result as Result.Error).type)
        assertEquals(mockException, result.exception)
        coVerify(exactly = 1) { mockRepository.getVoucher(voucherId) }
    }
}
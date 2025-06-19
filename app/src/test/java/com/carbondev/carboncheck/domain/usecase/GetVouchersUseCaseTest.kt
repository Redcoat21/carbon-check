package com.carbondev.carboncheck.domain.usecase

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Voucher
import com.carbondev.carboncheck.domain.repository.VoucherRepository
import com.carbondev.carboncheck.domain.usecase.voucher.GetVouchersUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetVouchersUseCaseTest {
    @MockK
    private lateinit var mockRepository: VoucherRepository
    private lateinit var getVouchersUseCase: GetVouchersUseCase


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getVouchersUseCase = GetVouchersUseCase(mockRepository)
    }

    @Test
    fun `invoke should return success when vouchers are founded`() = runTest {
        // Arrange
        val mockVouchers = listOf<Voucher>(
            mockk(),
            mockk(),
            mockk()
        )
        coEvery { mockRepository.getVouchers() } returns Result.Success(mockVouchers)

        // Act
        val result = getVouchersUseCase()

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(mockVouchers, (result as Result.Success).data)
    }

    @Test
    fun `invoke should return success even if no vouchers are founded`() = runTest {
        // Arrange
        val mockVouchers = emptyList<Voucher>()
        coEvery { mockRepository.getVouchers() } returns Result.Success(mockVouchers)

        // Act
        val result = getVouchersUseCase()
        // Assert
        assertTrue(result is Result.Success)
        assertEquals(mockVouchers, (result as Result.Success).data)
    }

    @Test
    fun `invoke should propagate repository error`() = runTest {
        // Arrange
        coEvery { mockRepository.getVouchers() } returns Result.Error(ErrorType.UNKNOWN_ERROR)

        // Act
        val result = getVouchersUseCase()

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.UNKNOWN_ERROR, (result as Result.Error).type)
    }
}
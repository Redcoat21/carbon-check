package com.carbondev.carboncheck.domain.usecase

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.repository.AuthRepository
import com.carbondev.carboncheck.domain.usecase.auth.LogoutUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LogoutUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var logoutUseCase: LogoutUseCase

    @Before
    fun setup() {
        authRepository = mockk()
        logoutUseCase = LogoutUseCase(authRepository)
    }

    @Test
    fun `invoke should return success when repository succeed`() = runTest {
        // Given
        coEvery { authRepository.logout() } returns Result.Success(Unit)

        // When
        val result = logoutUseCase.invoke()

        // Then
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { authRepository.logout() }
    }

    @Test
    fun `invoke should return error when repository logout fails`() = runTest {
        // Given
        val errorMessage = "Logout failed"
        coEvery { authRepository.logout() } returns Result.Error(
            message = errorMessage,
            type = mockk()
        )

        // When
        val result = logoutUseCase.invoke()

        // Then
        assertTrue(result is Result.Error)
    }
}
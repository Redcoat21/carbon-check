package com.carbondev.carboncheck.domain.usecase

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.repository.AuthRepository
import com.carbondev.carboncheck.domain.usecase.auth.LoginWithEmailAndPasswordUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LoginWithEmailAndPasswordUseCaseTest {
    @MockK
    private lateinit var mockRepository: AuthRepository
    @MockK
    private lateinit var mockErrorHandler: ErrorHandler
    private lateinit var loginWithEmailAndPasswordUseCase: LoginWithEmailAndPasswordUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        loginWithEmailAndPasswordUseCase =
            LoginWithEmailAndPasswordUseCase(mockRepository, mockErrorHandler)
        every { mockErrorHandler.mapToDomainError(any<IllegalArgumentException>()) } returns ErrorType.VALIDATION_ERROR
    }

    @Test
    fun `invoke should return success when log in succeed`() = runTest {
        // Arrange
        var email = "test@example.com"
        var password = "password123!"
        coEvery { mockRepository.loginWithEmail(email, password) } returns Result.Success(data = Unit)
        // Act
        val result = loginWithEmailAndPasswordUseCase(email = email, password = password)
        // Assert
        assertTrue(result is Result.Success)
    }

    @Test
    fun `invoke should return error when validation failed`() = runTest {
        // Arrange
        var email = "invalid-email"
        var password = "short"
        // Act
        val result = loginWithEmailAndPasswordUseCase(email = email, password = password)
        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.VALIDATION_ERROR, (result as Result.Error).type)
    }
}
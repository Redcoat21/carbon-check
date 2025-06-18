package com.carbondev.carboncheck.domain.usecase

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.User
import com.carbondev.carboncheck.domain.repository.UserRepository
import com.carbondev.carboncheck.domain.usecase.user.GetCurrentUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCurrentUserUseCaseTest {
    @MockK
    private lateinit var mockRepository: UserRepository
    private lateinit var useCase: GetCurrentUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetCurrentUserUseCase(mockRepository)
    }

    @Test
    fun `invoke should return a success result when repository returned success`() = runTest {
        // Arrange
        val mockUser = mockk<User>()
        coEvery { mockRepository.getCurrentUser() } returns Result.Success(mockUser)
        // Act
        val result = useCase.invoke()
        // Assert
        assertTrue(result is Result.Success)
        assertEquals((result as Result.Success).data, mockUser)
    }

    @Test
    fun `invoke should return a failed result with a not found error when repository returned failed`() = runTest {
        // Arrange
        coEvery { mockRepository.getCurrentUser() } returns Result.Error(
            type = ErrorType.NOT_FOUND_ERROR,
            exception = Exception("The impossible had happened!")
        )
        // Act
        val result = useCase.invoke()
        // Assert
        assertTrue(result is Result.Error)
        assertEquals((result as Result.Error).type, ErrorType.NOT_FOUND_ERROR)

    }
}
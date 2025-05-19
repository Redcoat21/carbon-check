package com.carbondev.carboncheck.domain.usecase

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.User
import com.carbondev.carboncheck.domain.repository.UserRepository
import com.carbondev.carboncheck.domain.usecase.profile.GetUserUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUserUseCaseTest {
    private lateinit var mockUserRepository: UserRepository

    @Before
    fun setUp() {
        mockUserRepository = mockk<UserRepository>()
    }

    @Test
    fun `invoke should return validation error when given invalid id`() = runTest {
        // Arrange
        val invalidId = ""
        val usecase = GetUserUseCase(mockUserRepository)

        coEvery {
            mockUserRepository.getUser(invalidId)
        } returns Result.Error(type = ErrorType.VALIDATION_ERROR, message = "Invalid user ID")

        // Act
        val result = usecase.invoke(invalidId)

        // Assert
        assert(result is Result.Error)
        assert((result as Result.Error).type == ErrorType.VALIDATION_ERROR)
    }

    @Test
    fun `invoke should return a user when given valid id`() = runTest {
        // Arrange
        val validId = "12345"
        val useCase = GetUserUseCase(mockUserRepository)
        val mockUser = mockk<User>()

        coEvery {
            mockUserRepository.getUser(validId)
        } returns Result.Success<User>(data = mockUser)

        // Act
        val result = useCase.invoke(validId)

        // Assert
        assert(result is Result.Success)
        assert((result as Result.Success).data == mockUser)
    }
}
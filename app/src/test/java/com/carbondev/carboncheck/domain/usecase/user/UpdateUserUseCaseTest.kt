package com.carbondev.carboncheck.domain.usecase.user

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.User
import com.carbondev.carboncheck.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UpdateUserUseCaseTest {

    private lateinit var mockUserRepository: UserRepository
    private lateinit var updateUserUseCase: UpdateUserUseCase

    @Before
    fun setUp() {
        mockUserRepository = mockk<UserRepository>()
        updateUserUseCase = UpdateUserUseCase(mockUserRepository)
    }

    @Test
    fun `invoke - successful update returns success result`() = runTest {
        // Arrange
        val userId = "user123"
        val user = User(
            id = userId,
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            avatarUrl = "https://example.com/avatar.jpg",
            createdAt = Clock.System.now()
        )
        val updatedUser = user.copy(firstName = "Updated", lastName = "Name")
        coEvery { mockUserRepository.updateUser(userId, updatedUser) } returns Result.Success(updatedUser)

        // Act
        val result = updateUserUseCase(userId, updatedUser)

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(updatedUser, (result as Result.Success).data)
    }

    @Test
    fun `invoke - repository error returns error result`() = runTest {
        // Arrange
        val userId = "user123"
        val user = User(
            id = userId,
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            avatarUrl = "https://example.com/avatar.jpg",
            createdAt = Clock.System.now()
        )
        val exception = RuntimeException("Network error")
        coEvery { mockUserRepository.updateUser(userId, user) } returns Result.Error(
            type = ErrorType.NETWORK_ERROR,
            exception = exception
        )

        // Act
        val result = updateUserUseCase(userId, user)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.NETWORK_ERROR, (result as Result.Error).type)
    }
}

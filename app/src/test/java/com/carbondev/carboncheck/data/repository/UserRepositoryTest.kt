package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.remote.model.NetworkUser
import com.carbondev.carboncheck.data.remote.repository.UserRepositoryRemoteImplementation
import com.carbondev.carboncheck.data.remote.supabase.UserRemoteDataSource
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.model.User
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

class UserRepositoryTest {
    @MockK private lateinit var mockRemoteDataSource: UserRemoteDataSource
    private lateinit var mockErrorHandler: ErrorHandler
    private lateinit var userRepository: UserRepositoryRemoteImplementation

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockErrorHandler = mockk(relaxed = true)
        userRepository = UserRepositoryRemoteImplementation(mockRemoteDataSource, mockErrorHandler)
    }

    @Test
    fun `getUser should return a valid user`(): Unit = runTest {
        val expectedProfileId = "12345"
        val mockNetworkUser = mockk<NetworkUser>()

        val expectedUser = mockk<User>()

        every { mockNetworkUser.toDomainModel() } returns expectedUser

        coEvery {
            mockRemoteDataSource.getUser(expectedProfileId)
        } returns mockNetworkUser

        // Act
        val result = userRepository.getUser(expectedProfileId)

        // Assert
        assertTrue(result is Result.Success)
        assertEquals((result as Result.Success).data, expectedUser)
    }

    @Test
    fun `getUser should return a not found error when user is not found`(): Unit = runTest {
        // Arrange
        val expectedProfileId = "invalid id"
        val expectedException = NoSuchElementException("User not found")

        every { mockErrorHandler.mapToDomainError(expectedException) } returns ErrorType.NOT_FOUND_ERROR
        coEvery {
            mockRemoteDataSource.getUser(expectedProfileId)
        } throws expectedException

        // Act
        val result = userRepository.getUser(expectedProfileId)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.NOT_FOUND_ERROR, (result as Result.Error).type)
    }

    @Test
    fun `getCurrentUser should return a valid user when current user exists`(): Unit = runTest {
        // Arrange
        val mockNetworkUser = mockk<NetworkUser>()
        val mockUser = mockk<User>()
        coEvery { mockRemoteDataSource.getCurrentUser() } returns mockNetworkUser
        every { mockNetworkUser.toDomainModel() } returns mockUser

        // Act
        val result = userRepository.getCurrentUser()

        // Assert
        assertTrue(result is Result.Success)
        assertEquals((result as Result.Success).data, mockUser)
    }

    @Test
    fun `getCurrentUser should return an error when current user does not exist`(): Unit = runTest {
        // Arrange
        val expectedException = IllegalStateException("User not found")
        coEvery { mockRemoteDataSource.getCurrentUser() } throws expectedException
        every { mockErrorHandler.mapToDomainError(expectedException) } returns ErrorType.NOT_FOUND_ERROR

        // Act
        val result = userRepository.getCurrentUser()

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.NOT_FOUND_ERROR, (result as Result.Error).type)
    }
}


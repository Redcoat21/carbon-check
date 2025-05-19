package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.remote.model.NetworkUser
import com.carbondev.carboncheck.data.remote.repository.UserRepositoryRemoteImplementation
import com.carbondev.carboncheck.data.remote.supabase.UserRemoteDataSource
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.error.ErrorHandler
import com.carbondev.carboncheck.domain.model.User
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {
    private lateinit var mockRemoteDataSource: UserRemoteDataSource
    private lateinit var profileRepository: UserRepositoryRemoteImplementation
    private lateinit var mockErrorHandler: ErrorHandler

    @Before
    fun setUp() {
        mockRemoteDataSource = mockk()
        mockErrorHandler = mockk()
        profileRepository = UserRepositoryRemoteImplementation(mockRemoteDataSource, mockErrorHandler)
    }

    @Test
    fun `getUser should return a valid profile`(): Unit = runTest {
        val expectedProfileId = "12345"
        val mockNetworkUser = mockk<NetworkUser>()

        val expectedUser = mockk<User>()

        every { mockNetworkUser.toDomainModel() } returns expectedUser

        coEvery {
            mockRemoteDataSource.getUser(expectedProfileId)
        } returns mockNetworkUser

        // Act
        val result = profileRepository.getUser(expectedProfileId)

        // Assert
        assert(result is Result.Success)
        assert((result as Result.Success).data == expectedUser)
    }

    @Test
    fun `getUser should return a not found error when user is not found`(): Unit = runTest {
        val expectedProfileId = "12345"

        coEvery {
            mockRemoteDataSource.getUser(expectedProfileId)
        } returns null

        // Act
        val result = profileRepository.getUser(expectedProfileId)

        // Assert
        assert(result is Result.Error)
        assert((result as Result.Error).type == ErrorType.NOT_FOUND_ERROR)
    }

}
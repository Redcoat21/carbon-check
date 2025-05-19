package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.remote.model.NetworkUser
import com.carbondev.carboncheck.data.remote.repository.UserRepositoryRemoteImplementation
import com.carbondev.carboncheck.data.remote.supabase.UserRemoteDataSource
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.error.ErrorHandler
import io.mockk.coEvery
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
        val mockNetworkUser = NetworkUser(
            id = expectedProfileId,
            firstName = "John",
            lastName = "Doe",
            createdAt = mockk(),
            updatedAt = mockk(),
            deletedAt = null,
            avatarUrl = "https://example.com/avatar.jpg",
            email = "johndoe@example.com",
        )

        val expectedProfile = mockNetworkUser.toDomainModel()

        coEvery {
            mockRemoteDataSource.getProfile(expectedProfileId)
        } returns mockNetworkUser

        // Act
        val result = profileRepository.getUser(expectedProfileId)

        // Assert
        assert(result is Result.Success)
        assert((result as Result.Success).data == expectedProfile)
    }

}
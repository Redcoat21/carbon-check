package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.remote.model.NetworkProfile
import com.carbondev.carboncheck.data.remote.repository.ProfileRepositoryRemoteImplementation
import com.carbondev.carboncheck.data.remote.supabase.ProfileRemoteDataSource
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.error.ErrorHandler
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ProfileRepositoryTest {
    private lateinit var mockRemoteDataSource: ProfileRemoteDataSource
    private lateinit var profileRepository: ProfileRepositoryRemoteImplementation
    private lateinit var mockErrorHandler: ErrorHandler

    @Before
    fun setUp() {
        mockRemoteDataSource = mockk()
        mockErrorHandler = mockk()
        profileRepository = ProfileRepositoryRemoteImplementation(mockRemoteDataSource, mockErrorHandler)
    }

    @Test
    fun `getProfile should return a valid profile`(): Unit = runTest {
        val expectedProfileId = "12345"
        val mockNetworkProfile = NetworkProfile(
            id = expectedProfileId,
            firstName = "John",
            lastName = "Doe",
            createdAt = mockk(),
            updatedAt = mockk(),
            deletedAt = null,
            avatarUrl = "https://example.com/avatar.jpg",
        )

        val expectedProfile = mockNetworkProfile.toDomainModel()

        coEvery {
            mockRemoteDataSource.getProfile(expectedProfileId)
        } returns mockNetworkProfile

        // Act
        val result = profileRepository.getProfile(expectedProfileId)

        // Assert
        assert(result is Result.Success)
        assert((result as Result.Success).data == expectedProfile)
    }

}
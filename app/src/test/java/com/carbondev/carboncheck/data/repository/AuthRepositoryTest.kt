package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.local.datasource.UserLocalDataSource
import com.carbondev.carboncheck.data.remote.supabase.AuthRemoteDataSource
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.repository.AuthRepository
import io.github.jan.supabase.exceptions.RestException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AuthRepositoryTest {
    @MockK
    private lateinit var remote: AuthRemoteDataSource
    @MockK
    private lateinit var local: UserLocalDataSource

    @MockK
    private lateinit var errorHandler: ErrorHandler
    private lateinit var repository: AuthRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        local = mockk(relaxed = true)
        repository = AuthRepositoryImplementation(remote,local, errorHandler)

        // Configure errorHandler to handle RestException
        every { errorHandler.mapToDomainError(any<RestException>()) } returns ErrorType.NETWORK_ERROR
    }

    @Test
    fun `loginWithEmail should return success when login is successful`() = runTest {
        // Arrange
        val email = "email@example.com"
        val password = "password123"
        coEvery { remote.loginWithEmailAndPassword(email, password) } returns mockk(relaxed = true)
        // Act
        val result = repository.loginWithEmail(email, password)
        // Assert
        assertTrue(result is Result.Success)
    }

    @Test
    fun `loginWithEmail should return fail when login is failed`() = runTest {
        // Arrange
        val email = ""
        val password = ""
        coEvery {
            remote.loginWithEmailAndPassword(
                email,
                password
            )
        } throws RestException(error = "", description = "", response = mockk(relaxed = true))
        // Act
        val result = repository.loginWithEmail(email, password)
        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.NETWORK_ERROR, (result as Result.Error).type)
    }

    @Test
    fun `logout should return success when logout is successful`() = runTest {
        // Arrange
        coJustRun { remote.logout() }
        coJustRun { local.deleteUser() }

        // Act
        val result = repository.logout()

        // Assert
        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { remote.logout() }
        coVerify(exactly = 1) { local.deleteUser() }
    }

    @Test
    fun `logout should return error when remote logout throws exception`() = runTest {
        // Arrange
        val exception = RestException(error = "Logout failed", description = "Network error", response = mockk(relaxed = true))
        coEvery { remote.logout() } throws exception

        // Act
        val result = repository.logout()

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.NETWORK_ERROR, (result as Result.Error).type)
        coVerify(exactly = 0) { local.deleteUser() } // Local data should not be cleared if remote logout fails
    }
}
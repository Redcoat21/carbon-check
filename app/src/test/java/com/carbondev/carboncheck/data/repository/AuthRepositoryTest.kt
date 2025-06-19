package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.remote.repository.AuthRepositoryImplementation
import com.carbondev.carboncheck.data.remote.supabase.AuthRemoteDataSource
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.repository.AuthRepository
import io.github.jan.supabase.exceptions.RestException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
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
    private lateinit var errorHandler: ErrorHandler
    private lateinit var repository: AuthRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = AuthRepositoryImplementation(remote, errorHandler)

        // Configure errorHandler to handle RestException
        every { errorHandler.mapToDomainError(any<RestException>()) } returns ErrorType.NETWORK_ERROR
    }

    @Test
    fun `loginWithEmail should return success when login is successful`() = runTest {
        // Arrange
        val email = "email@example.com"
        val password = "password123"
        coJustRun { remote.loginWithEmailAndPassword(email, password) }
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
}
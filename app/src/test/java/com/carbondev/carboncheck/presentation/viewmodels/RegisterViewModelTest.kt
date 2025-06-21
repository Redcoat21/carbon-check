package com.carbondev.carboncheck.presentation.viewmodels

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.usecase.auth.RegisterWithEmailAndPasswordUseCase
import com.carbondev.carboncheck.presentation.auth.viewmodel.RegisterViewModel
import com.carbondev.carboncheck.presentation.common.UiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: RegisterViewModel

    @MockK
    private lateinit var mockRegisterWithEmailAndPasswordUseCase: RegisterWithEmailAndPasswordUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        viewModel = RegisterViewModel(mockRegisterWithEmailAndPasswordUseCase)
    }

    @Test
    fun `UI state should be empty initially`() = runTest {
        assertTrue(viewModel.uiState.value is UiState.Empty)
    }

    @Test
    fun `UI state should be loading when register is called`() = runTest {
        // Arrange
        val email = "johndoe@example.com"
        val password = "Password123!"
        val confirmPassword = password
        val firstName = "John"
        val lastName = "Doe"
        coEvery {
            mockRegisterWithEmailAndPasswordUseCase(
                email,
                password,
                confirmPassword,
                firstName,
                lastName
            )
        } coAnswers {
            delay(1000) // suspends to let us check loading state
            com.carbondev.carboncheck.domain.common.Result.Success(Unit)
        }
        // Act
        viewModel.register(email, password, confirmPassword, firstName, lastName)
        runCurrent()
        // Assert
        assertTrue(viewModel.uiState.value is UiState.Loading)
    }

    @Test
    fun `UI state should be success when login is successful`() = runTest {
        // Arrange
        val email = "johndoe"
        val password = "Password123!"
        val confirmPassword = password
        val firstName = "John"
        val lastName = "Doe"
        coEvery { mockRegisterWithEmailAndPasswordUseCase(email, password, confirmPassword, firstName, lastName) } coAnswers {
            delay(1000) // suspends to let us check loading state
            com.carbondev.carboncheck.domain.common.Result.Success(Unit)
        }
        // Act
        viewModel.register(email, password, confirmPassword, firstName, lastName)
        advanceUntilIdle()
        // Assert
        assertTrue(viewModel.uiState.value is UiState.Success<*>)
    }

    @Test
    fun `UI state should be error when login fails`() = runTest {
        // Arrange
        val email = "johndoe"
        val password = "Password123!"
        val confirmPassword = password
        val firstName = "John"
        val lastName = "Doe"
        coEvery { mockRegisterWithEmailAndPasswordUseCase(email, password, confirmPassword, firstName, lastName) } coAnswers {
            delay(1000) // suspends to let us check loading state
            Result.Error(type = mockk(relaxed = true))
        }
        // Act
        viewModel.register(email, password, confirmPassword, firstName, lastName)
        advanceUntilIdle()
        // Assert
        assertTrue(viewModel.uiState.value is UiState.Error)
    }
}
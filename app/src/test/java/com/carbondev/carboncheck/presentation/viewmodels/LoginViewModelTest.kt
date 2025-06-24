package com.carbondev.carboncheck.presentation.viewmodels

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.usecase.auth.LoginWithEmailAndPasswordUseCase
import com.carbondev.carboncheck.presentation.auth.viewmodel.LoginViewModel
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
class LoginViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel

    @MockK
    private lateinit var mockLoginWithEmailAndPasswordUseCase: LoginWithEmailAndPasswordUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        viewModel = LoginViewModel(mockLoginWithEmailAndPasswordUseCase, mockk(relaxed = true))
    }

    @Test
    fun `UI state should be empty initially`() = runTest {
        assertTrue(viewModel.uiState.value is UiState.Empty)
    }

    @Test
    fun `UI state should be loading when login is called`() = runTest {
        // Arrange
        val email = "johndoe@example.com"
        val password = "Password123!"
        coEvery { mockLoginWithEmailAndPasswordUseCase(email, password) } coAnswers {
            delay(1000) // suspends to let us check loading state
            Result.Success(Unit)
        }
        // Act
        viewModel.login(email, password)
        runCurrent()
        // Assert
        assertTrue(viewModel.uiState.value is UiState.Loading)
    }

    @Test
    fun `UI state should be error when login fails`() = runTest {
        // Arrange
        val email = "johndoe"
        val password = "Password123!"
        coEvery { mockLoginWithEmailAndPasswordUseCase(email, password) } coAnswers {
            delay(1000) // suspends to let us check loading state
            Result.Error(type = mockk(relaxed = true))
        }
        // Act
        viewModel.login(email, password)
        advanceUntilIdle()
        // Assert
        assertTrue(viewModel.uiState.value is UiState.Error)
    }
}
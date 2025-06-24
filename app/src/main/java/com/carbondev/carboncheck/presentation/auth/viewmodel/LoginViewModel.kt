package com.carbondev.carboncheck.presentation.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.usecase.activity.RefreshActivitiesUseCase
import com.carbondev.carboncheck.domain.usecase.auth.LoginWithEmailAndPasswordUseCase
import com.carbondev.carboncheck.presentation.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithEmailAndPasswordUseCase: LoginWithEmailAndPasswordUseCase,
    private val refreshActivitiesUseCase: RefreshActivitiesUseCase
) : BaseViewModel() {

    fun login(email: String, password: String) = viewModelScope.launch {
        setLoading()

        // 1. Attempt to log in
        val loginResult = loginWithEmailAndPasswordUseCase(email = email, password = password)

        // Guard clause for login failure.
        // If the login is not successful, set the error and exit the coroutine immediately.
        if (loginResult is Result.Error) {
            val errorMessage = when (loginResult.type) {
                ErrorType.NETWORK_ERROR -> "Invalid credentials. Please try again."
                else -> loginResult.message ?: "An unexpected error occurred. Please try again."
            }
            setError(errorMessage)
            return@launch
        }

        // 2. Since login was successful, attempt to refresh activities.
        val refreshResult = refreshActivitiesUseCase()

        // Guard clause for activity refresh failure.
        // If refreshing fails, set an appropriate error and exit.
        if (refreshResult is Result.Error) {
            val errorMessage = refreshResult.message ?: "Login successful, but failed to refresh data."
            setError(errorMessage)
            return@launch
        }

        if (loginResult is Result.Success) {
            // 3. Happy Path: Both login and activity refresh were successful.
            // The compiler can smart-cast loginResult to Result.Success here because of the guard clause.
            setSuccess(loginResult.data)
        }

    }
}
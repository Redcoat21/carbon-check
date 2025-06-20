package com.carbondev.carboncheck.presentation.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.usecase.auth.LoginWithEmailAndPasswordUseCase
import com.carbondev.carboncheck.presentation.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginWithEmailAndPasswordUseCase: LoginWithEmailAndPasswordUseCase) :
    BaseViewModel() {

    fun login(email: String, password: String) = viewModelScope.launch {
        setLoading()
        when (val result =
            loginWithEmailAndPasswordUseCase(email = email, password = password)) {
            is Result.Success -> {
                // Login successful
                setSuccess(result.data)
            }

            is Result.Error -> {
                // Handle different error types
                val errorMessage = when (result.type) {
                    ErrorType.NETWORK_ERROR -> "Invalid credentials. Please try again."
                    else -> result.message ?: "An unexpected error occurred. Please try again."
                }
                setError(errorMessage)
            }
        }

    }
}


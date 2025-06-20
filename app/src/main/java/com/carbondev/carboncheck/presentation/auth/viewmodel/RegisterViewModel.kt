package com.carbondev.carboncheck.presentation.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.usecase.auth.RegisterWithEmailAndPasswordUseCase // Assuming this use case exists
import com.carbondev.carboncheck.presentation.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerWithEmailAndPasswordUseCase: RegisterWithEmailAndPasswordUseCase
) : BaseViewModel() {

    fun register(email: String, password: String, confirmPassword: String, firstName: String, lastName: String) = viewModelScope.launch {
        setLoading()

        when (val result =
            registerWithEmailAndPasswordUseCase(
                email = email,
                password = password,
                confirmPassword = confirmPassword,
//                firstName = firstName,
//                lastName = lastName
            )) {
            is Result.Success -> {
                setSuccess(result.data)
            }

            is Result.Error -> {
                val errorMessage = when (result.type) {
                    ErrorType.NETWORK_ERROR -> "Registration failed. Please try again."
                    else -> result.message ?: "An unexpected error occurred. Please try again."
                }
                setError(errorMessage)
            }
        }
    }
}
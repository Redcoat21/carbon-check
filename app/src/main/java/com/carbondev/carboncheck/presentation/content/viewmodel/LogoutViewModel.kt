package com.carbondev.carboncheck.presentation.content.viewmodel

import androidx.lifecycle.viewModelScope
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.usecase.auth.LogoutUseCase
import com.carbondev.carboncheck.presentation.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(private val logoutUseCase: LogoutUseCase) : BaseViewModel() {
    fun logout() = viewModelScope.launch {
        setLoading()
        when (val result =
            logoutUseCase()) {
            is Result.Success -> {
                setSuccess(result.data)
            }

            is Result.Error -> {
                val errorMessage = "An unexpected error occurred. Please try again."
                setError(errorMessage)
            }
        }
    }
}

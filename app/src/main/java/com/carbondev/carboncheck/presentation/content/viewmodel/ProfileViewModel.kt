package com.carbondev.carboncheck.presentation.content.viewmodel

import androidx.lifecycle.viewModelScope
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.usecase.user.GetCurrentUserUseCase
import com.carbondev.carboncheck.presentation.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val firstName: String = "",
    val lastName: String = "",
    val avatarUrl: String = "",
    val email: String = ""
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : BaseViewModel() {

    init {
        loadData()
    }

    fun loadData() = viewModelScope.launch {
        setLoading()

        val userResult = getCurrentUserUseCase()

        if (userResult is Result.Error) {
            val errorMessage = when (userResult.type) {
                ErrorType.NOT_FOUND_ERROR -> "Your profile could not be found. Please try logging in again."
                ErrorType.NETWORK_ERROR -> "Please check your internet connection and try again."
                else -> userResult.message ?: "An unexpected error occurred while loading your profile."
            }
            setError(errorMessage)
            return@launch
        }

        val user = (userResult as Result.Success).data

        setSuccess(
            ProfileUiState(
                firstName = user.firstName,
                lastName = user.lastName,
                avatarUrl = user.avatarUrl,
                email = user.email
            )
        )
    }
}
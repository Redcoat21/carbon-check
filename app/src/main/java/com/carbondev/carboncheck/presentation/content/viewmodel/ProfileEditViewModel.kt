package com.carbondev.carboncheck.presentation.content.viewmodel

import androidx.lifecycle.viewModelScope
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.User
import com.carbondev.carboncheck.domain.usecase.user.GetCurrentUserUseCase
import com.carbondev.carboncheck.domain.usecase.user.UpdateUserUseCase
import com.carbondev.carboncheck.presentation.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileEditUiState(
    val firstName: String = "",
    val lastName: String = "",
    val avatarUrl: String = ""
)

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseViewModel() {

    private var currentUser: User? = null

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
        currentUser = user

        setSuccess(
            ProfileEditUiState(
                firstName = user.firstName,
                lastName = user.lastName,
                avatarUrl = user.avatarUrl
            )
        )
    }

    fun updateUser(firstName: String, lastName: String, avatarUrl: String) = viewModelScope.launch {
        val user = currentUser ?: return@launch

        val updatedUser = user.copy(
            firstName = firstName,
            lastName = lastName,
            avatarUrl = avatarUrl
        )

        setLoading()

        val result = updateUserUseCase(user.id, updatedUser)

        if (result is Result.Error) {
            val msg = result.message ?: "Failed to update profile"
            setError(msg)
            return@launch
        }

        currentUser = updatedUser
        setSuccess(
            ProfileEditUiState(
                firstName = updatedUser.firstName,
                lastName = updatedUser.lastName,
                avatarUrl = updatedUser.avatarUrl
            )
        )
    }
}


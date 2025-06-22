package com.carbondev.carboncheck.presentation.common.viewmodel

import androidx.lifecycle.ViewModel
import com.carbondev.carboncheck.presentation.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Base ViewModel class that provides common UI state management functionality.
 * All ViewModels that need to work with UiState should extend this class.
 */
abstract class BaseViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState

    /**
     * Updates the UI state to Loading
     */
    protected fun setLoading() {
        _uiState.value = UiState.Loading
    }

    /**
     * Updates the UI state to Empty
     */
    protected fun setEmpty() {
        _uiState.value = UiState.Empty
    }

    /**
     * Updates the UI state to Success with the provided data
     */
    protected fun <T> setSuccess(data: T) {
        _uiState.value = UiState.Success(data)
    }

    /**
     * Updates the UI state to Error with the provided message
     */
    protected fun setError(message: String) {
        _uiState.value = UiState.Error(message)
    }
}
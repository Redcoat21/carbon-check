package com.carbondev.carboncheck.presentation.common

/**
 * Represents the UI state of a screen or component.
 * This sealed class can be used to manage different states such as loading, success, error, and empty.
 */
sealed class UiState {
    object Loading : UiState()
    data class Success<T>(val data: T) : UiState()
    data class Error(val message: String) : UiState()
    object Empty : UiState()
}
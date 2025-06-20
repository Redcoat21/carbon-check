package com.carbondev.carboncheck.presentation.common

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.carbondev.carboncheck.presentation.common.component.LoadingRing
import com.carbondev.carboncheck.presentation.common.viewmodel.BaseViewModel

/**
 * A composable that handles common UI state patterns like showing loading indicators and error messages.
 * This version doesn't use generic type parameters to avoid type inference issues.
 *
 * @param viewModel The ViewModel that extends BaseViewModel and provides a UI state
 * @param onSuccess Called when the UI state is Success. The success data is provided as a parameter.
 * @param content The content to display when not in loading state
 */
@Composable
fun UiStateHandler(
    viewModel: BaseViewModel,
    onSuccess: @Composable (data: Any?) -> Unit = {},
    content: @Composable () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Handle error state - show toast with error message
    if (uiState is UiState.Error) {
        val errorMessage = (uiState as UiState.Error).message
        LaunchedEffect(errorMessage) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    // Handle success state
    if (uiState is UiState.Success<*>) {
        val data = (uiState as UiState.Success<*>).data
        onSuccess(data)
    }

    // Show loading or content
    if (uiState is UiState.Loading) {
        LoadingRing()
    } else {
        content()
    }
}
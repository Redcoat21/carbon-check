package com.carbondev.carboncheck.domain.common

/**
 * Represents the result of an operation, which can either be successful or an error.
 *
 * @param T The type of data returned on success.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val type: ErrorType,
        val message: String? = null,
        val exception: Throwable? = null
    ) : Result<Nothing>()
}


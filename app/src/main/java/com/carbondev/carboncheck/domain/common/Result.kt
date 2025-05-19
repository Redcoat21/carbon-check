package com.carbondev.carboncheck.domain.common

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val type: ErrorType,
        val message: String? = null,
        val exception: Throwable? = null
    ) : Result<Nothing>()
}


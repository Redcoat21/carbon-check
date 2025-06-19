package com.carbondev.carboncheck.domain.exception

import com.carbondev.carboncheck.domain.common.ErrorType
import io.github.jan.supabase.exceptions.RestException
import kotlinx.io.IOException
import javax.inject.Singleton

/**
 * Interface for handling errors in the domain layer.
 * It maps exceptions to domain-specific error types.
 */
@Singleton
class ErrorHandler {
    fun mapToDomainError(exception: Throwable): ErrorType {
        return when (exception) {
            is IOException, is RestException -> ErrorType.NETWORK_ERROR
            is NoSuchElementException -> ErrorType.NOT_FOUND_ERROR
            else -> ErrorType.UNKNOWN_ERROR
        }
    }
}
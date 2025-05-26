package com.carbondev.carboncheck.domain.error

import com.carbondev.carboncheck.domain.common.ErrorType

/**
 * Interface for handling errors in the domain layer.
 * It maps exceptions to domain-specific error types.
 */
interface ErrorHandler {
    fun mapToDomainError(exception: Exception): ErrorType
}
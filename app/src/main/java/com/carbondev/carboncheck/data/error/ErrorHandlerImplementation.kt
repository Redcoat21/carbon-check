package com.carbondev.carboncheck.data.error

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.error.ErrorHandler
import kotlinx.io.IOException

class ErrorHandlerImplementation : ErrorHandler {
    override fun mapToDomainError(exception: Exception): ErrorType {
        return when (exception) {
            is IOException -> ErrorType.NETWORK_ERROR
            else -> ErrorType.UNKNOWN_ERROR
        }
    }
}
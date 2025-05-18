package com.carbondev.carboncheck.domain.error

import com.carbondev.carboncheck.domain.common.ErrorType

interface ErrorHandler {
    fun mapToDomainError(exception: Exception): ErrorType
}
package com.carbondev.carboncheck.domain.exception

import com.carbondev.carboncheck.domain.common.ErrorType
import org.junit.Test
import junit.framework.TestCase.assertEquals

class ErrorHandlerTest {
    private val errorHandler = ErrorHandler()

    @Test
    fun `mapToDomainError should return NETWORK_ERROR for IOException`() {
        val exception = java.io.IOException("Network error")
        val result = errorHandler.mapToDomainError(exception)
        assertEquals(result, ErrorType.NETWORK_ERROR)
    }

    @Test
    fun `mapToDomainError should return NOT_FOUND_ERROR for NoSuchElementException`() {
        val exception = NoSuchElementException("Not found")
        val result = errorHandler.mapToDomainError(exception)
        assertEquals(result, ErrorType.NOT_FOUND_ERROR)
    }

    @Test
    fun `mapToDomainError should return UNKNOWN_ERROR for other exceptions`() {
        val exception = Exception("Unknown error")
        val result = errorHandler.mapToDomainError(exception)
        assertEquals(result, ErrorType.UNKNOWN_ERROR)
    }
}
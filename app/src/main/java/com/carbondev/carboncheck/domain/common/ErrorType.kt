package com.carbondev.carboncheck.domain.common

/**
 * Enum class representing different types of errors that can occur in the application.
 */
enum class ErrorType {
    NETWORK_ERROR, // Error related to network connectivity issues
    DATABASE_ERROR, // Error related to database operations
    UNKNOWN_ERROR, // An error that does not fit into any specific category
    NOT_FOUND_ERROR, // Error indicating that a requested resource was not found
    VALIDATION_ERROR, // Error indicating that some input validation failed
}
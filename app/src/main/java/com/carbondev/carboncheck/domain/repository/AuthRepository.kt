package com.carbondev.carboncheck.domain.repository

import com.carbondev.carboncheck.domain.common.Result

interface AuthRepository {
    /**
     * Logs in a user with the specified email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return A result indicating success or failure of the login operation.
     */
    suspend fun loginWithEmail(email: String, password: String): Result<Unit>

    /**
     * Registers a new user with the specified email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return A result indicating success or failure of the registration operation.
     */
    suspend fun registerWithEmail(email: String, password: String): Result<Unit>
}
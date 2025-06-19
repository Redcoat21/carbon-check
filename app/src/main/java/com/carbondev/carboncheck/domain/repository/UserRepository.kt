package com.carbondev.carboncheck.domain.repository

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.User

/**
 * Repository interface for managing user data.
 */
interface UserRepository {
    /**
     * Get a user by their ID.
     * @param id The ID of the user to retrieve.
     * @return A [Result] containing the user data or an error.
     */
    suspend fun getUser(id: String): Result<User>

    /**
     * Get the current authenticated user.
     * @return A [Result] containing the current user data or an error.
     */
    suspend fun getCurrentUser(): Result<User>
}
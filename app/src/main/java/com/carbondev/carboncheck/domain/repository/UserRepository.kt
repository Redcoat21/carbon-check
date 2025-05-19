package com.carbondev.carboncheck.domain.repository

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.User

/**
 * Repository interface for managing user data.
 */
interface UserRepository {
    suspend fun getUser(id: String): Result<User?>
}
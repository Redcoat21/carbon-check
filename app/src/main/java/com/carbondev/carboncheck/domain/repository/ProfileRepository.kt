package com.carbondev.carboncheck.domain.repository

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Profile

/**
 * Repository interface for managing user profiles.
 */
interface ProfileRepository {
    suspend fun getProfile(id: String): Result<Profile?>
}
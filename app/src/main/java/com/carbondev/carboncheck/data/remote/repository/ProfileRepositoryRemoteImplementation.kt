package com.carbondev.carboncheck.data.remote.repository

import com.carbondev.carboncheck.data.remote.supabase.ProfileRemoteDataSource
import com.carbondev.carboncheck.domain.error.ErrorHandler
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Profile
import com.carbondev.carboncheck.domain.repository.ProfileRepository
import javax.inject.Inject

/**
 * Implementation of [ProfileRepository] that fetches data from a remote data source.
 *
 * @param remote The remote data source to fetch profile data from.
 */
class ProfileRepositoryRemoteImplementation @Inject constructor(
    private val remote: ProfileRemoteDataSource,
    private val errorHandler: ErrorHandler
) :
    ProfileRepository {
    override suspend fun getProfile(id: String): Result<Profile?> {
        return try {
            remote.getProfile(id)?.let { profile ->
                Result.Success(profile.toDomainModel())
            } ?: Result.Error(message = "Profile not found", type = ErrorType.NOT_FOUND_ERROR)
        } catch (e: Exception) {
            Result.Error(type = errorHandler.mapToDomainError(e), exception = e)
        }
    }
}
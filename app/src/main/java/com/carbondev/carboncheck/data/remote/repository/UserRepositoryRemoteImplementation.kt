package com.carbondev.carboncheck.data.remote.repository

import com.carbondev.carboncheck.data.remote.supabase.UserRemoteDataSource
import com.carbondev.carboncheck.domain.error.ErrorHandler
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.User
import com.carbondev.carboncheck.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Implementation of [UserRepository] that fetches data from a remote data source.
 *
 * @param remote The remote data source to fetch user data from.
 */
class UserRepositoryRemoteImplementation @Inject constructor(
    private val remote: UserRemoteDataSource,
    private val errorHandler: ErrorHandler
) :
    UserRepository {
    override suspend fun getUser(id: String): Result<User?> {
        return try {
            remote.getProfile(id)?.let { user ->
                Result.Success(user.toDomainModel())
            } ?: Result.Error(message = "User not found", type = ErrorType.NOT_FOUND_ERROR)
        } catch (e: Exception) {
            Result.Error(type = errorHandler.mapToDomainError(e), exception = e)
        }
    }
}
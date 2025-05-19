package com.carbondev.carboncheck.data.remote.repository

import com.carbondev.carboncheck.data.remote.supabase.UserRemoteDataSource
import com.carbondev.carboncheck.domain.error.ErrorHandler
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.User
import com.carbondev.carboncheck.domain.repository.UserRepository
import timber.log.Timber
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
            remote.getUser(id)?.let { user ->
                Result.Success(user.toDomainModel())
            } ?: run {
                Timber.w("User with id $id not found")
                Result.Error(message = "User not found", type = ErrorType.NOT_FOUND_ERROR)
            }
        } catch (e: Exception) {
            Timber.e("Error fetching user $id: ${e.message}")
            Result.Error(type = errorHandler.mapToDomainError(e), exception = e)
        }
    }
}
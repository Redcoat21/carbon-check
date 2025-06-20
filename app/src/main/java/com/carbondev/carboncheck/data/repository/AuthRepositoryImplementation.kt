package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.remote.supabase.AuthRemoteDataSource
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImplementation @Inject constructor(
    private val remote: AuthRemoteDataSource,
    private val errorHandler: ErrorHandler
) : AuthRepository {
    override suspend fun loginWithEmail(email: String, password: String): Result<Unit> {
        return runCatching {
            remote.loginWithEmailAndPassword(email = email, password = password)
        }.fold(onSuccess = {
            Result.Success(Unit)
        }, onFailure = {
            Timber.w("Login failed with email: $email, error: ${it.message}")
            Result.Error(type = errorHandler.mapToDomainError(it), exception = it)
        })
    }

    override suspend fun registerWithEmail(email: String, password: String): Result<Unit> {
        return runCatching {
            remote.registerWithEmailAndPassword(email = email, password = password)
        }.fold(onSuccess = { Result.Success(Unit) }, onFailure = {
            Timber.w("Registration failed with email: $email, error: ${it.message}")
            Result.Error(type = errorHandler.mapToDomainError(it), exception = it)
        })
    }
}
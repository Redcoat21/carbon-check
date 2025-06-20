package com.carbondev.carboncheck.domain.usecase.auth

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class LoginWithEmailAndPasswordUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        if (email.isBlank() || password.isBlank()) {
            val message = "Email or password cannot be blank"
            Timber.e(message)
            return Result.Error(
                type = ErrorType.VALIDATION_ERROR,
                message = message
            )
        }
        return repository.loginWithEmail(email = email, password = password)
    }
}
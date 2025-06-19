package com.carbondev.carboncheck.domain.usecase.auth

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.repository.AuthRepository
import com.carbondev.carboncheck.domain.validation.ValidationRules
import io.konform.validation.Validation
import javax.inject.Inject

class LoginWithEmailAndPasswordUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val errorHandler: ErrorHandler
) {
    private data class Params(val email: String, val password: String)

    private val validator = Validation<Params> {
        Params::email {
            run(ValidationRules.email)
        }
        Params::password {
            run(ValidationRules.password)
        }
    }

    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        val validationResult = validator.validate(Params(email, password))
        if (validationResult.errors.isNotEmpty()) {
            return Result.Error(
                exception = IllegalArgumentException("Invalid email or password format"),
                type = ErrorType.VALIDATION_ERROR,
                message = validationResult.errors.joinToString(", ")
            )
        }

        return runCatching {
            repository.loginWithEmail(email = email, password = password)
        }.fold(onSuccess = {
            Result.Success(data = Unit)
        }, onFailure = {
            Result.Error(exception = it, type = errorHandler.mapToDomainError(exception = it))
        })
    }
}
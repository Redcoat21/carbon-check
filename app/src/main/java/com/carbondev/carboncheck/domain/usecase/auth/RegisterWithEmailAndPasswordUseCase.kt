package com.carbondev.carboncheck.domain.usecase.auth

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.repository.AuthRepository
import com.carbondev.carboncheck.domain.validation.ValidationRules
import io.konform.validation.Validation
import javax.inject.Inject

class RegisterWithEmailAndPasswordUseCase @Inject constructor(
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
            val exception = IllegalArgumentException("Invalid email or password format")
            return Result.Error(
                exception = exception,
                type = errorHandler.mapToDomainError(exception),
                message = validationResult.errors.joinToString(", ")
            )
        }
        return repository.registerWithEmail(email, password)
    }
}
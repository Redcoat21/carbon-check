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
    private data class Params(val email: String, val password: String, val confirmPassword: String)

    private val validator = Validation<Params> {
        Params::email {
            run(ValidationRules.email)
        }
        Params::password {
            run(ValidationRules.password)
        }
        Params::confirmPassword {
            run(ValidationRules.password)
        }
    }

    suspend operator fun invoke(email: String, password: String, confirmPassword: String, firstName: String, lastName: String): Result<Unit> {
        if (email.isBlank()) {
            val exception = IllegalArgumentException("Email cannot be blank")
            return Result.Error(
                exception = exception,
                type = errorHandler.mapToDomainError(exception),
                message = "Email cannot be blank"
            )
        }
        if (password.isBlank()) {
            val exception =
                IllegalArgumentException("Password cannot be blank")
            return Result.Error(
                exception = exception,
                type = errorHandler.mapToDomainError(exception),
                message = "Password cannot be blank"
            )
        }
        if (confirmPassword.isBlank()) {
            val exception =
                IllegalArgumentException("Confirm Password cannot be blank")
            return Result.Error(
                exception = exception,
                type = errorHandler.mapToDomainError(exception),
                message = "Confirm Password cannot be blank"
            )
        }
        if (firstName.isBlank()) {
            val exception =
                IllegalArgumentException("First Name cannot be blank")
            return Result.Error(
                exception = exception,
                type = errorHandler.mapToDomainError(exception),
                message = "First Name cannot be blank"
            )
        }
        if (lastName.isBlank()) {
            val exception =
                IllegalArgumentException("Last Name cannot be blank")
            return Result.Error(
                exception = exception,
                type = errorHandler.mapToDomainError(exception),
                message = "Last Name cannot be blank"
            )
        }
        val validationResult = validator.validate(Params(email, password, confirmPassword))
        if (validationResult.errors.isNotEmpty()) {
            val exception = IllegalArgumentException("Invalid email or password format")
            return Result.Error(
                exception = exception,
                type = errorHandler.mapToDomainError(exception),
                message = validationResult.errors[0].message
            )
        }
        if (password != confirmPassword) {
            val exception = IllegalArgumentException("Passwords do not match")
            return Result.Error(
                exception = exception,
                type = errorHandler.mapToDomainError(exception),
                message = "Passwords do not match"
            )
        }
        return repository.registerWithEmail(email, password)
    }
}
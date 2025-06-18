package com.carbondev.carboncheck.domain.usecase.user

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.User
import com.carbondev.carboncheck.domain.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: String): Result<User> {
        if(id.isEmpty() || id.isBlank()) {
            Timber.w("Invalid user ID: $id")
            return Result.Error(type = ErrorType.VALIDATION_ERROR,message = "User ID cannot be empty or blank")
        }

        return userRepository.getUser(id)
    }
}
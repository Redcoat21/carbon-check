package com.carbondev.carboncheck.domain.usecase.user

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.User
import com.carbondev.carboncheck.domain.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Result<User> {
        return userRepository.getCurrentUser()
    }
}
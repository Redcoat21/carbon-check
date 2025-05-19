package com.carbondev.carboncheck.domain.usecase.profile

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.User
import com.carbondev.carboncheck.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: String): Result<User?> {
        return userRepository.getUser(id)
    }
}
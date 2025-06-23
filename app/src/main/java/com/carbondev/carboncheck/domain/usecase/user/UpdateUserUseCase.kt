package com.carbondev.carboncheck.domain.usecase.user

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.User
import com.carbondev.carboncheck.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    /**
     * Updates user information
     *
     * @param id The ID of the user to update
     * @param user The updated user data
     * @return A [Result] containing either the updated [User] or an error
     */
    suspend operator fun invoke(id: String, user: User): Result<User> {
        return userRepository.updateUser(id, user)
    }
}
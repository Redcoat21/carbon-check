package com.carbondev.carboncheck.domain.usecase.profile

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Profile
import com.carbondev.carboncheck.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(id: String): Result<Profile?> {
        return profileRepository.getProfile(id)
    }
}
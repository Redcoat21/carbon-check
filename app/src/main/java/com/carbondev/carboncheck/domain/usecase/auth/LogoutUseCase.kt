package com.carbondev.carboncheck.domain.usecase.auth

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.logout()
    }
}
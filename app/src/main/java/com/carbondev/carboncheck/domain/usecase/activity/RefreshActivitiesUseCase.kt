package com.carbondev.carboncheck.domain.usecase.activity

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.repository.ActivityRepository
import javax.inject.Inject

class RefreshActivitiesUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.refreshActivities()
    }
}
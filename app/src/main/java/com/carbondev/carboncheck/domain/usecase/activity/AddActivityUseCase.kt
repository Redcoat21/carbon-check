package com.carbondev.carboncheck.domain.usecase.activity

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Activity
import com.carbondev.carboncheck.domain.repository.ActivityRepository
import javax.inject.Inject

/**
 * A use case dedicated to adding a single new activity.
 * This encapsulates the business logic for this specific action, keeping the ViewModel clean.
 */
class AddActivityUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(activity: Activity): Result<Activity> {
        return repository.addActivity(activity)
    }
}
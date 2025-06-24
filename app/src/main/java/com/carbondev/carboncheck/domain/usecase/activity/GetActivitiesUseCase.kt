package com.carbondev.carboncheck.domain.usecase.activity

import com.carbondev.carboncheck.domain.model.Activity
import com.carbondev.carboncheck.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * A use case that provides a continuous stream of the current user's activities.
 *
 * The UI layer should collect this Flow to get real-time updates when the
 * underlying data in the repository changes.
 */
class GetActivitiesUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    /**
     * Invokes the use case.
     * @return A [Flow] that emits a list of [Activity] objects.
     */
    suspend operator fun invoke(): Flow<List<Activity>> {
        // Simply return the flow from the repository. The repository, as the
        // single source of truth, handles providing the cached data stream.
        return repository.getActivities()
    }
}
package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.local.ActivityLocalDataSource
import com.carbondev.carboncheck.data.remote.ActivityRemoteDataSource
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Activity
import com.carbondev.carboncheck.domain.repository.ActivityRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import javax.inject.Inject

/**
 * Implementation of the ActivityRepository.
 * It uses the local database as the single source of truth and coordinates updates
 * with the remote data source.
 */
class ActivityRepositoryImplementation @Inject constructor(
    private val localDataSource: ActivityLocalDataSource,
    private val remoteDataSource: ActivityRemoteDataSource,
    private val client: SupabaseClient // Needed to get the current user ID
) : ActivityRepository {

    /**
     * Returns a direct flow from the local database. The UI observes this.
     */
    override fun getActivities(): Flow<List<Activity>> {
        // FIX: Handle the case where the user is not logged in.
        val userId = client.auth.currentUserOrNull()?.id
        return if (userId != null) {
            localDataSource.getActivitiesForUser(userId)
        } else {
            // If no user, return an empty list to avoid errors.
            flowOf(emptyList())
        }
    }

    /**
     * Fetches from remote and saves to local.
     */
    override suspend fun refreshActivities(): Result<Unit> {
        return try {
            val remoteActivities = remoteDataSource.getActivitiesForUser()
            if (remoteActivities != null) {
                Timber.d("Refresh successful. Fetched ${remoteActivities.size} activities from remote.")
                // POTENTIAL ISSUE: clearAllActivities() will wipe data for ALL users on the device.
                // If this is a multi-user app, you should have a method like
                // localDataSource.clearActivitiesForCurrentUser(userId)
                localDataSource.clearAllActivities()
                localDataSource.upsertActivities(remoteActivities)
                Result.Success(Unit)
            } else {
                Timber.w("Refresh failed: Remote data source returned null.")
                Result.Error(
                    type = ErrorType.NETWORK_ERROR,
                    message = "Could not fetch latest activities."
                )
            }
        } catch (e: Exception) {
            Timber.e(e, "Exception during activity refresh.")
            Result.Error(
                type = ErrorType.UNKNOWN_ERROR,
                message = e.message ?: "An unknown error occurred.",
                exception = e
            )
        }
    }

    /**
     * Pushes change to remote first, then updates local cache.
     */
    override suspend fun addActivity(activity: Activity): Result<Activity> {
        val createdActivity = remoteDataSource.addActivity(activity)

        return if (createdActivity != null) {
            Timber.d("Successfully added activity to remote. Caching locally.")
            // On success, update the local cache with the single new item.
            localDataSource.upsertActivities(listOf(createdActivity))
            Result.Success(createdActivity)
        } else {
            Timber.e("Failed to add activity to remote.")
            Result.Error(
                type = ErrorType.NETWORK_ERROR,
                message = "Failed to save activity. Please try again."
            )
        }
    }

    /**
     * Deletes from remote first, then from the local cache.
     */
    override suspend fun deleteActivity(activityId: String): Result<Unit> {
        val deleteSuccess = remoteDataSource.deleteActivity(activityId)

        return if (deleteSuccess) {
            Timber.d("Successfully deleted activity from remote. Removing from local cache.")
            // On success, remove from the local cache.
            // This requires a deleteById method in your DAO and LocalDataSource.
            localDataSource.deleteActivityById(activityId)
            Result.Success(Unit)
        } else {
            Timber.e("Failed to delete activity from remote.")
            Result.Error(
                type = ErrorType.NETWORK_ERROR,
                message = "Failed to delete activity. Please try again."
            )
        }
    }
}

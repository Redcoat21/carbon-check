package com.carbondev.carboncheck.domain.repository

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Activity
import kotlinx.coroutines.flow.Flow

/**
 * The repository that provides a single source of truth for Activity data.
 */
interface ActivityRepository {

    /**
     * Retrieves a stream of the user's activities from the local cache.
     * This flow will automatically update when the underlying data changes.
     */
    fun getActivities(): Flow<List<Activity>>

    /**
     * Fetches the latest activities from the remote source and updates the local cache.
     *
     * @return A [Result] indicating whether the refresh was successful.
     */
    suspend fun refreshActivities(): Result<Unit>

    /**
     * Adds a new activity. It is sent to the remote source first, then saved locally on success.
     *
     * @param activity The activity to be added.
     * @return A [Result] containing the server-confirmed [Activity] on success.
     */
    suspend fun addActivity(activity: Activity): Result<Activity>

    /**
     * Deletes an activity. It is deleted from the remote source first, then locally on success.
     *
     * @param activityId The ID of the activity to delete.
     * @return A [Result] indicating whether the deletion was successful.
     */
    suspend fun deleteActivity(activityId: String): Result<Unit>
}
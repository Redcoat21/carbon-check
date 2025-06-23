package com.carbondev.carboncheck.data.local

import com.carbondev.carboncheck.data.local.dao.ActivityDao
import com.carbondev.carboncheck.data.local.entity.ActivityEntity
import com.carbondev.carboncheck.domain.model.Activity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface ActivityLocalDataSource {
    suspend fun upsertActivities(activities: List<Activity>)
    fun getActivitiesForUser(userId: String): Flow<List<Activity>>
    suspend fun clearAllActivities()
    suspend fun deleteActivityById(activityId: String)
}

/**
 * Provides a clean API for interacting with the local activities database.
 * It uses the ActivityDao and handles mapping between entity and domain models.
 */
@Singleton
class ActivityLocalDataSourceImplementation @Inject constructor(
    private val activityDao: ActivityDao
): ActivityLocalDataSource {
    /**
     * Retrieves a flow of all activities for a user, mapped to the domain model.
     */
    override fun getActivitiesForUser(userId: String): Flow<List<Activity>> {
        return activityDao.getActivitiesForUser(userId)
            .map { entityList ->
                entityList.map { it.toDomainModel() }
            }
    }

    /**
     * Inserts or updates a list of domain model activities into the local database.
     */
    override suspend fun upsertActivities(activities: List<Activity>) {
        val entities = activities.map { ActivityEntity.fromDomainModel(it) }
        activityDao.upsertAll(entities)
    }

    /**
     * Clears all activities from the local cache.
     */
    override suspend fun clearAllActivities() {
        activityDao.deleteAll()
    }

    /**
     * Deletes an activity by its ID.
     */
    override suspend fun deleteActivityById(activityId: String) {
        activityDao.deleteActivityById(activityId)
    }
}
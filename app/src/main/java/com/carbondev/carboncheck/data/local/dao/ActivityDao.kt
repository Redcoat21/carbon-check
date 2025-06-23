package com.carbondev.carboncheck.data.local.dao

import androidx.room.*
import com.carbondev.carboncheck.data.local.entity.ActivityEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the 'activities' table.
 */
@Dao
interface ActivityDao {
    /**
     * Inserts a list of activities into the database. If an activity with the same
     * primary key already exists, it will be replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(activities: List<ActivityEntity>)

    /**
     * Retrieves all activities for a specific user, ordered by most recent first.
     * Returns a Flow, which will automatically emit new lists when the data changes.
     */
    @Query("SELECT * FROM activities WHERE user_id = :userId ORDER BY datetime DESC")
    fun getActivitiesForUser(userId: String): Flow<List<ActivityEntity>>

    /**
     * Retrieves a single activity by its ID.
     */
    @Query("SELECT * FROM activities WHERE activity_id = :activityId")
    fun getActivityById(activityId: String): Flow<ActivityEntity?>

    /**
     * Deletes all activities from the table. Used for clearing the cache.
     */
    @Query("DELETE FROM activities")
    suspend fun deleteAll()

    /**
     * Deletes a single activity by its ID.
     */
    @Query("DELETE FROM activities WHERE activity_id = :activityId")
    suspend fun deleteActivityById(activityId: String)
}
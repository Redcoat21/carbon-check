package com.carbondev.carboncheck.data.remote

import androidx.compose.foundation.text.input.insert
import com.carbondev.carboncheck.data.remote.model.NetworkActivity
import com.carbondev.carboncheck.domain.model.Activity
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.query.Returning
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class is responsible for handling activity-related operations with Supabase.
 * It uses the Supabase client to perform CRUD operations on the 'activities' table.
 *
 * @param client The Supabase client used to make network requests.
 */
@Singleton
class ActivityRemoteDataSource @Inject constructor(
    private val client: SupabaseClient
) {
    /**
     * Fetches a list of activities for the currently authenticated user.
     *
     * @return A list of domain [Activity] objects on success, or null if the fetch fails or no user is logged in.
     */
    suspend fun getActivitiesForUser(): List<Activity>? {
        return try {
            // Step 1: Ensure a user is authenticated
            val authUser = client.auth.currentUserOrNull() ?: return null
            Timber.tag("ActivityDS").d("Fetching activities for user: ${authUser.id}")

            // Step 2: Fetch activities from the "activities" table
            val networkActivities = client.postgrest
                .from(NetworkActivity.TABLE_NAME)
                .select(Columns.ALL) {
                    filter {
                        eq(NetworkActivity.Columns.USER_ID, authUser.id)
                    }
                    order(NetworkActivity.Columns.DATETIME, order = Order.DESCENDING)
                }.decodeList<NetworkActivity>()

            Timber.tag("ActivityDS").d("Successfully fetched ${networkActivities.size} activities.")

            // Step 3: Map the network models to domain models and return
            networkActivities.map { it.toDomainModel() }
        } catch (e: Exception) {
            Timber.tag("ActivityDS").e(e, "Failed to fetch activities")
            return null
        }
    }

    /**
     * Adds a new activity to the remote database for the currently authenticated user.
     *
     * @param activity The domain model of the activity to add. The userId field will be overwritten
     *                 by the currently authenticated user's ID for security.
     * @return The newly created domain [Activity] object on success, or null on failure.
     */
    suspend fun addActivity(activity: Activity): Activity? {
        return try {
            val authUser = client.auth.currentUserOrNull() ?: return null

            val networkActivityToInsert = NetworkActivity(
                userId = authUser.id, // Ensure we use the authenticated user's ID
                type = activity.type,
                datetime = activity.datetime,
                carbon = activity.carbon.gram.toInt(),
                flightDestination = activity.flightDestination,
                flightDeparture = activity.flightDeparture,
                people = activity.people,
                distance = activity.distance,
                food = activity.foodType,
                weight = activity.weightInGrams,
                vehicle = activity.vehicleType
            )

           val createdNetworkActivity = client.postgrest
                .from(NetworkActivity.TABLE_NAME)
                .insert(networkActivityToInsert) {
                    select()
                }
                .decodeSingle<NetworkActivity>()
            Timber.tag("ActivityDS").d("Successfully added activity: ${createdNetworkActivity.activityId}")

            createdNetworkActivity.toDomainModel()
        } catch (e: Exception) {
            Timber.tag("ActivityDS").e("Failed to add activity: ${e.message}")
            return null
        }
    }

    /**
     * Deletes an activity from the remote database.
     *
     * @param activityId The UUID of the activity to delete.
     * @return `true` on success, `false` on failure.
     */
    suspend fun deleteActivity(activityId: String): Boolean {
        return try {
            client.postgrest.from(NetworkActivity.TABLE_NAME).delete {
                filter {
                    eq(NetworkActivity.Columns.ACTIVITY_ID, activityId)
                }
            }
            Timber.tag("ActivityDS").d("Successfully deleted activity: $activityId")
            true
        } catch (e: Exception) {
            Timber.tag("ActivityDS").e(e, "Failed to delete activity: $activityId")
            false
        }
    }
}
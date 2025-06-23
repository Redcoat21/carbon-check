package com.carbondev.carboncheck.domain.usecase.activity

import com.carbondev.carboncheck.domain.model.Activity
import com.carbondev.carboncheck.domain.model.CarbonData
import com.carbondev.carboncheck.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

/**
 * A use case that calculates the total carbon emissions for the current day.
 *
 * It observes the full list of activities and processes it to return a single,
 * continuously updated CarbonData object representing today's total.
 */
class GetTodaysCarbonTotalUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    /**
     * Invokes the use case.
     * @return A [Flow] that emits a [CarbonData] object representing the sum of today's emissions.
     */
    operator fun invoke(): Flow<CarbonData> {
        // Get the stream of all activities from the repository.
        val activitiesFlow: Flow<List<Activity>> = repository.getActivities()

        // Use the .map operator to transform the list of activities into a single CarbonData total.
        return activitiesFlow.map { allActivities ->
            // Determine the current date in the user's system timezone.
            val systemTimeZone = TimeZone.currentSystemDefault()
            val today = Clock.System.now().toLocalDateTime(systemTimeZone).date

            // 1. Filter the list to keep only activities that occurred today.
            val todaysActivities = allActivities.filter { activity ->
                activity.datetime.toLocalDateTime(systemTimeZone).date == today
            }

            // 2. Sum the carbon footprint of today's activities.
            // We use fold, starting with an empty CarbonData object, and add each
            // activity's carbon value to the accumulator.
            todaysActivities.fold(CarbonData()) { accumulator, activity ->
                accumulator + activity.carbon
            }
        }
    }
}
package com.carbondev.carboncheck.data.remote.model

import com.carbondev.carboncheck.domain.common.ActivityType
import com.carbondev.carboncheck.domain.model.Activity
import com.carbondev.carboncheck.domain.model.CarbonData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.datetime.Instant

@JsonClass(generateAdapter = true)
data class NetworkActivity(
    @Json(name = "activity_id") val activityId: String,
    @Json(name = "user_id") val userId: String,
    val type: ActivityType,
    val datetime: Instant,
    val carbon: Int, // Assumed to be in grams from the database
    @Json(name = "flight_destination") val flightDestination: String?,
    @Json(name = "flight_departure") val flightDeparture: String?,
    val people: Int?,
    val distance: Int?,
    val food: String?,
    val weight: Int?,
    val vehicle: String?
) : RemoteMappable<Activity> {

    companion object {
        const val TABLE_NAME = "activities"
    }

    object Columns {
        const val ACTIVITY_ID = "activity_id"
        const val USER_ID = "user_id"
        const val TYPE = "type"
        const val DATETIME = "datetime"
        const val CARBON = "carbon"
        const val FLIGHT_DESTINATION = "flight_destination"
        const val FLIGHT_DEPARTURE = "flight_departure"
        const val PEOPLE = "people"
        const val DISTANCE = "distance"
        const val FOOD = "food"
        const val WEIGHT = "weight"
        const val VEHICLE = "vehicle"

        val ALL = arrayOf(
            ACTIVITY_ID,
            USER_ID,
            TYPE,
            DATETIME,
            CARBON,
            FLIGHT_DESTINATION,
            FLIGHT_DEPARTURE,
            PEOPLE,
            DISTANCE,
            FOOD,
            WEIGHT,
            VEHICLE
        )
    }

    /**
     * Converts the raw network data into a clean, usable domain model.
     */
    override fun toDomainModel(): Activity {
        // Convert the carbon value (assumed grams) into the complex CarbonData object.
        val carbonInGrams = carbon.toDouble()
        val carbonData = CarbonData(
            gram = carbonInGrams,
            kilogram = carbonInGrams / 1000.0,
            pound = carbonInGrams * 0.00220462,
            metricTon = carbonInGrams / 1_000_000.0
        )

        return Activity(
            id = activityId,
            userId = userId,
            type = type,
            datetime = datetime,
            carbon = carbonData,
            flightDestination = flightDestination,
            flightDeparture = flightDeparture,
            people = people,
            distance = distance,
            foodType = food,
            weightInGrams = weight,
            vehicleType = vehicle
        )
    }
}

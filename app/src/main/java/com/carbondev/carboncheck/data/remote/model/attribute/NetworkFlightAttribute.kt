package com.carbondev.carboncheck.data.remote.model.attribute

import com.carbondev.carboncheck.data.remote.model.request.NetworkFlightLeg
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.datetime.Instant

/**
 * DTO for the attribute in flight response from the Carbon Interface API.
 * @property passengers the number of passengers for the flight.
 * @property legs the list of flight legs, each leg represents a segment of the flight.
 * @property distanceUnit the unit of distance, e.g., "km" or "mi".
 * @property distanceValue the value of distance in the specified unit.
 */
@JsonClass(generateAdapter = true)
data class NetworkFlightAttribute(
    val passengers: Int,
    val legs: List<NetworkFlightLeg>,
    @Json(name = "estimated_at") override val estimatedAt: Instant,
    @Json(name = "carbon_g") override val carbonG: Double,
    @Json(name = "carbon_kg") override val carbonKg: Double,
    @Json(name = "carbon_lb") override val carbonLb: Double,
    @Json(name = "carbon_mt") override val carbonMt: Double,
    @Json(name = "distance_unit") val distanceUnit: String,
    @Json(name = "distance_value") val distanceValue: Double
) : NetworkAttributeResponse()
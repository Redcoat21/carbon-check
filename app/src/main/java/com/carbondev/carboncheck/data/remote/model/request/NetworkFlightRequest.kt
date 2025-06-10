package com.carbondev.carboncheck.data.remote.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * DTO for the leg object in the flight request to the Carbon Interface API.
 * @property departureAirport the IATA code of the departure airport.
 * @property destinationAirport the IATA code of the destination airport.
 * @property cabinClass the cabin class for the flight, can be "economy" or "premium", defaulted to "economy".
 */
@JsonClass(generateAdapter = true)
data class NetworkFlightLeg(
    @Json(name = "departure_airport") val departureAirport: String,
    @Json(name = "destination_airport") val destinationAirport: String,
    @Json(name = "cabin_class") val cabinClass: String = "economy"
)

/**
 * DTO for the request of type flight to the Carbon Interface API.
 * @property passengers the number of passengers for the flight.
 * @property legs the list of flight legs, each containing departure and destination airports and cabin class.
 * @property distanceUnit the unit of distance, either "km" or "mi". Defaulted to "km".
 */
@JsonClass(generateAdapter = true)
data class NetworkFlightRequest(
    val passengers: Int,
    val legs: List<NetworkFlightLeg>,
    @Json(name = "distance_unit") val distanceUnit: String = "km"
) : NetworkRequest() {
    override val type: String = "flight"
}

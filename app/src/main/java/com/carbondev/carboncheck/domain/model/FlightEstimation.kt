package com.carbondev.carboncheck.domain.model

import com.carbondev.carboncheck.data.remote.model.request.NetworkFlightLeg
import kotlinx.datetime.Instant

/**
 * Data class representing a flight leg.
 * @property departureAirport the IATA code of the departure airport.
 * @property destinationAirport the IATA code of the destination airport.
 * @property cabinClass the cabin class for the flight leg (e.g., "economy", "business").
 */
data class FlightLeg(
    val departureAirport: String,
    val destinationAirport: String,
    val cabinClass: String
) {
    fun toRemoteModel(): NetworkFlightLeg {
        return NetworkFlightLeg(
            departureAirport = departureAirport,
            destinationAirport = destinationAirport,
            cabinClass = cabinClass
        )
    }
}

/**
 * Data class representing a flight estimation.
 * @property id the unique identifier for the flight estimation.
 * @property passengers the number of passengers for the flight.
 * @property legs the list of flight legs, each leg represents a segment of the flight.
 * @property carbonData the carbon data associated with the flight estimation.
 * @property estimatedAt the timestamp when the data was estimated, in ISO 8601 format in UTC Time format as an [Instant].
 * @property distance a pair representing the distance unit (e.g., "km", "mi") and the distance value in [Double].
 */
data class FlightEstimation(
    val id: String,
    val passengers: Int,
    val legs: List<FlightLeg>,
    val carbonData: CarbonData,
    val estimatedAt: Instant,
    val distance: Pair<String, Double>
)

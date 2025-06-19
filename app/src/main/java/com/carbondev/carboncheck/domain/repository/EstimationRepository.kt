package com.carbondev.carboncheck.domain.repository

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.FlightEstimation
import com.carbondev.carboncheck.domain.model.FlightLeg

interface EstimationRepository {
    /**
     * Calculates the carbon footprint estimation for a flight based on the number of passengers and flight legs.
     * @param passengers The number of passengers on the flight.
     * @param legs A list of flight legs, each containing details about the flight segment.
     * @param distanceUnit The unit of distance to be used in the estimation (default is "km").
     * @return A [Result] containing the FlightEstimation or an error.
     */
    suspend fun getFlightEstimation(passengers: Int, legs: List<FlightLeg>, distanceUnit: String = "km"): Result<FlightEstimation>
}
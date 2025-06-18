package com.carbondev.carboncheck.domain.repository

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.FlightEstimation
import com.carbondev.carboncheck.domain.model.FlightLeg

interface EstimationRepository {
    suspend fun getFlightEstimation(passengers: Int, legs: List<FlightLeg>, distanceUnit: String = "km"): Result<FlightEstimation>
}
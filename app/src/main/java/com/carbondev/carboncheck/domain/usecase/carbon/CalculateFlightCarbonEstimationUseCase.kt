package com.carbondev.carboncheck.domain.usecase.carbon

import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.model.FlightEstimation
import com.carbondev.carboncheck.domain.model.FlightLeg
import com.carbondev.carboncheck.domain.repository.EstimationRepository
import javax.inject.Inject

class CalculateFlightCarbonEstimationUseCase @Inject constructor(
    private val repository: EstimationRepository,
    private val errorHandler: ErrorHandler
) {
    suspend operator fun invoke(
        passengers: Int,
        legs: List<FlightLeg>,
        distanceUnit: String = "km"
    ): Result<FlightEstimation> {
        // Validate input parameters
        when {
            passengers < 1 -> return createError("Number of passengers must be at least 1")
            legs.isEmpty() -> return createError("Flight legs cannot be empty")
            distanceUnit !in listOf("km", "mi") -> return createError("Distance unit must be either 'km' or 'mi'")
        }

        return repository.getFlightEstimation(
            passengers = passengers,
            legs = legs,
            distanceUnit = distanceUnit
        )
    }

    private fun createError(message: String): Result.Error {
        val exception = IllegalArgumentException(message)
        return Result.Error(exception = exception, type = errorHandler.mapToDomainError(exception))
    }
}
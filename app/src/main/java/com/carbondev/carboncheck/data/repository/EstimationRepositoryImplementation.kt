package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.remote.api.CarbonInterfaceService
import com.carbondev.carboncheck.data.remote.model.ApiResponse
import com.carbondev.carboncheck.data.remote.model.CarbonInterfaceData
import com.carbondev.carboncheck.data.remote.model.attribute.NetworkAttribute
import com.carbondev.carboncheck.data.remote.model.request.NetworkFlightRequest
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.model.CarbonData
import com.carbondev.carboncheck.domain.model.FlightEstimation
import com.carbondev.carboncheck.domain.model.FlightLeg
import com.carbondev.carboncheck.domain.repository.EstimationRepository
import kotlinx.io.IOException
import timber.log.Timber
import javax.inject.Inject

class EstimationRepositoryImplementation @Inject constructor(
    private val service: CarbonInterfaceService,
    private val errorHandler: ErrorHandler
) : EstimationRepository {
    override suspend fun getFlightEstimation(
        passengers: Int,
        legs: List<FlightLeg>,
        distanceUnit: String
    ): Result<FlightEstimation> {
        return runCatching {
            service.getFlightEstimation(
                request = NetworkFlightRequest(
                    passengers,
                    legs.map { it.toRemoteModel() },
                    distanceUnit
                )
            ).let { evaluateApiResponse(it) }
        }.fold(
            onSuccess = { response ->
                with(response) {
                    Result.Success(
                        FlightEstimation(
                            id = id,
                            passengers = attributes.passengers,
                            legs = attributes.legs.map {
                                FlightLeg(it.departureAirport, it.destinationAirport, it.cabinClass)
                            },
                            carbonData = CarbonData(
                                attributes.carbonG,
                                attributes.carbonKg,
                                attributes.carbonLb,
                                attributes.carbonMt
                            ),
                            estimatedAt = attributes.estimatedAt,
                            distance = attributes.distanceUnit to attributes.distanceValue
                        )
                    )
                }
            },
            onFailure = { error ->
                Timber.w("Error fetching flight estimation: ${error.message}")
                Result.Error(errorHandler.mapToDomainError(error), exception = error)
            }
        )
    }

    private fun <T : NetworkAttribute> evaluateApiResponse(response: ApiResponse<T>): CarbonInterfaceData<T> {
        if (response.isSuccessful) {
            val data = response.body()
            if (data == null) {
                throw IOException("Response body is null")
            }
            return data.data
        } else {
            throw IOException(
                "API call failed with code ${response.code()}: ${
                    response.errorBody()?.string()
                }"
            )
        }
    }
}
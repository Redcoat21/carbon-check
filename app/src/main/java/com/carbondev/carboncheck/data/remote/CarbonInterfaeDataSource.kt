package com.carbondev.carboncheck.data.remote

import com.carbondev.carboncheck.data.remote.api.CarbonInterfaceService
import com.carbondev.carboncheck.data.remote.model.attribute.NetworkFlightAttribute
import com.carbondev.carboncheck.data.remote.model.request.NetworkFlightRequest
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarbonInterfaceDataSource @Inject constructor(
    private val carbonInterfaceService: CarbonInterfaceService
) {
    /**
     * Fetches a flight's carbon estimation from the Carbon Interface API.
     * @return The NetworkFlightAttribute containing carbon data on success, or null on failure.
     */
    suspend fun getFlightCarbonEstimation(request: NetworkFlightRequest): NetworkFlightAttribute? {
        return try {
            val response = carbonInterfaceService.getFlightEstimation(request)
            if (response.isSuccessful) {
                val flightAttribute = response.body()?.data?.attributes
                Timber.d("Successfully fetched flight estimation: ${flightAttribute?.carbonG}g")
                flightAttribute
            } else {
                Timber.e("Failed to fetch flight estimation. Code: ${response.code()}, Message: ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Timber.e(e, "Exception occurred while fetching flight estimation.")
            null
        }
    }
}
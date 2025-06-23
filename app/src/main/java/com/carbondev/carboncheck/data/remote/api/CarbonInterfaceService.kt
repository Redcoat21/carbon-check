package com.carbondev.carboncheck.data.remote.api

import com.carbondev.carboncheck.data.remote.model.ApiResponse
import com.carbondev.carboncheck.data.remote.model.attribute.NetworkElectricityAttribute
import com.carbondev.carboncheck.data.remote.model.attribute.NetworkFlightAttribute
import com.carbondev.carboncheck.data.remote.model.attribute.NetworkShippingAttribute
import com.carbondev.carboncheck.data.remote.model.request.NetworkElectricityRequest
import com.carbondev.carboncheck.data.remote.model.request.NetworkFlightRequest
import com.carbondev.carboncheck.data.remote.model.request.NetworkShippingRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface CarbonInterfaceService {
    @POST("/api/v1/estimates")
    suspend fun getElectricityEstimation(@Body request: NetworkElectricityRequest): ApiResponse<NetworkElectricityAttribute>

    @POST("/api/v1/estimates")
    suspend fun getFlightEstimation(@Body request: NetworkFlightRequest): ApiResponse<NetworkFlightAttribute>

    @POST("/api/v1/estimates")
    suspend fun getShippingEstimation(@Body request: NetworkShippingRequest): ApiResponse<NetworkShippingAttribute>
}
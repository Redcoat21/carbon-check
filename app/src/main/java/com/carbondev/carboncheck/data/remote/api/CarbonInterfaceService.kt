package com.carbondev.carboncheck.data.remote.api

import com.carbondev.carboncheck.data.remote.model.NetworkResponse
import com.carbondev.carboncheck.data.remote.model.attribute.NetworkElectricityAttribute
import com.carbondev.carboncheck.data.remote.model.attribute.NetworkFlightAttribute
import com.carbondev.carboncheck.data.remote.model.attribute.NetworkShippingAttribute
import com.carbondev.carboncheck.data.remote.model.request.NetworkElectricityRequest
import com.carbondev.carboncheck.data.remote.model.request.NetworkFlightRequest
import com.carbondev.carboncheck.data.remote.model.request.NetworkShippingRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface CarbonInterfaceService {
    @POST
    suspend fun getElectricityEstimation(@Body request: NetworkElectricityRequest): NetworkResponse<NetworkElectricityAttribute>

    @POST
    suspend fun getFlightEstimation(@Body request: NetworkFlightRequest): NetworkResponse<NetworkFlightAttribute>

    @POST
    suspend fun getShippingEstimation(@Body request: NetworkShippingRequest): NetworkResponse<NetworkShippingAttribute>
}
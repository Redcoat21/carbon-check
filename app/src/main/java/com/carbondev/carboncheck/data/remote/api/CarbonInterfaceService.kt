package com.carbondev.carboncheck.data.remote.api

import com.carbondev.carboncheck.data.remote.model.NetworkResponse
import com.carbondev.carboncheck.data.remote.model.attribute.NetworkElectricityAttribute
import com.carbondev.carboncheck.data.remote.model.NetworkElectricityRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface CarbonInterfaceService {
    @POST
    suspend fun getElectricityEstimation(@Body request: NetworkElectricityRequest): NetworkResponse<NetworkElectricityAttribute>
}
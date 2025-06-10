package com.carbondev.carboncheck.data.remote.model

/**
 * DTO for the request of type electricity to the Carbon Interface API.
 * @property electricityUnit the unit of electricity, either "mwh" or "kwh". Defaulted to "mwh"
 * @property electricityValue the value of electricity in the specified unit
 * @property country the country code (ISO 3166-1 alpha-2) for the request, only support European and North American countries.
 * @property state the state code (ISO 3166-2) for the request, optional and only applicable for US states and Canadian Provinces.
 * @property type the type of request, always "electricity" for this DTO.
 */
@com.squareup.moshi.JsonClass(generateAdapter = true)
data class NetworkElectricityRequest(
    @com.squareup.moshi.Json(name = "electricity_unit") val electricityUnit: String = "mwh",
    @com.squareup.moshi.Json(name = "electricity_value") val electricityValue: Int,
    val country: String,
    val state: String? = null
) {
    val type: String = "electricity"
}
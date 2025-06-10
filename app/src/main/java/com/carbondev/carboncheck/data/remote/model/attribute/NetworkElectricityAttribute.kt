package com.carbondev.carboncheck.data.remote.model.attribute

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.datetime.Instant

/**
 * DTO for the attribute in electricity response from the Carbon Interface API.
 * @property country the country code (ISO 3166-1 alpha-2) for the request, only support European and North American countries.
 * @property state the state code (ISO 3166-2) for the request, optional and only applicable for US states and Canadian Provinces.
 * @property electricityUnit the unit of electricity, either "mwh" or "kwh".
 * @property electricityValue the value of electricity in the specified unit.
 */
@JsonClass(generateAdapter = true)
data class NetworkElectricityAttribute(
    val country: String,
    val state: String,
    @Json(name = "electricity_unit") val electricityUnit: String,
    @Json(name = "electricity_value") val electricityValue: Int,
    @Json(name = "estimated_at") override val estimatedAt: Instant,
    @Json(name = "carbon_g") override val carbonG: Double,
    @Json(name = "carbon_lb") override val carbonLb: Double,
    @Json(name = "carbon_kg") override val carbonKg: Double,
    @Json(name = "carbon_mt") override val carbonMt: Double,
) : NetworkAttribute()
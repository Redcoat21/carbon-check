package com.carbondev.carboncheck.data.remote.model.attribute

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.datetime.Instant

/**
 * DTO for the attribute in shipping response from the Carbon Interface API.
 * @property distanceValue the value of distance in the specified unit.
 * @property distanceUnit the unit of distance (e.g., "km", "mi").
 * @property weightValue the value of weight in the specified unit.
 * @property weightUnit the unit of weight (e.g., "g", "lb", "kg", "mt").
 * @property transportMethod the method of transport (e.g., "truck", "train", "plane", "ship").
 */
@JsonClass(generateAdapter = true)
data class NetworkShippingAttribute(
    @Json(name = "distance_value") val distanceValue: Double,
    @Json(name = "distance_unit") val distanceUnit: String,
    @Json(name = "weight_value") val weightValue: Double,
    @Json(name = "weight_unit") val weightUnit: String,
    @Json(name = "transport_method") val transportMethod: String,
    @Json(name = "estimated_at") override val estimatedAt: Instant,
    @Json(name = "carbon_g") override val carbonG: Double,
    @Json(name = "carbon_lb") override val carbonLb: Double,
    @Json(name = "carbon_kg") override val carbonKg: Double,
    @Json(name = "carbon_mt") override val carbonMt: Double,
) : NetworkAttribute()

package com.carbondev.carboncheck.data.remote.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * DTO for the request of type shipping to the Carbon Interface API.
 * @property weightUnit the unit of weight (e.g., "g", "lb", "kg", "mt").
 * @property weightValue the value of the weight.
 * @property distanceUnit the unit of distance (e.g., "km", "mi").
 * @property distanceValue the value of the distance.
 * @property transportMethod the method of transport (e.g., "truck", "train", "plane", "ship").
 */
@JsonClass(generateAdapter = true)
data class NetworkShippingRequest(
    @Json(name = "weight_unit") val weightUnit: String,
    @Json(name = "weight_value") val weightValue: Double,
    @Json(name = "distance_unit") val distanceUnit: String,
    @Json(name = "distance_value") val distanceValue: Double,
    @Json(name = "transport_method") val transportMethod: String
) : NetworkRequest() {
    override val type: String = "shipping"
}

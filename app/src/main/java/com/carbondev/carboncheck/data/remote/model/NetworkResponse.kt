package com.carbondev.carboncheck.data.remote.model

import com.carbondev.carboncheck.data.remote.model.attribute.NetworkAttributeResponse
import com.squareup.moshi.JsonClass

/**
 * DTO for the response from the Carbon Interface API.
 * @property id the unique identifier for the requested data.
 * @property type the type of data, usually it would be "estimate"
 * @property attributes the attributes of the requested data, including carbon weight and other details.
 */
@JsonClass(generateAdapter = true)
data class NetworkResponse<T : NetworkAttributeResponse>(
    val id: String,
    val type: String,
    val attributes: T
)
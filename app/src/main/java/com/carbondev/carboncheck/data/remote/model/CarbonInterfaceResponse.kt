package com.carbondev.carboncheck.data.remote.model

import com.carbondev.carboncheck.data.remote.model.attribute.NetworkAttribute
import com.squareup.moshi.JsonClass
import retrofit2.Response

/**
 * Type alias for the Retrofit response that contains the Carbon Interface API response.
 * This is used to simplify the type signature in Retrofit service interfaces, and thus should always be preferred.
 * @param T the type of NetworkAttribute that contains the carbon estimate details.
 */
typealias ApiResponse<T> = Response<CarbonInterfaceResponse<T>>

/**
 * DTO for the response from the Carbon Interface API.
 * Not to be confused with [CarbonInterfaceData] since this is the top-level response object.
 * @property data the data key containing the requested carbon estimate information.
 * @param T the type of NetworkAttribute that contains the carbon estimate details.
 */
@JsonClass(generateAdapter = true)
data class CarbonInterfaceResponse<T : NetworkAttribute>(
    val data: CarbonInterfaceData<T>
)

/**
 * DTO for the data key in the response from the Carbon Interface API.
 * @property id the unique identifier for the requested data.
 * @property type the type of data, usually it would be "estimate"
 * @property attributes the attributes of the requested data, including carbon weight and other details.
 */
@JsonClass(generateAdapter = true)
data class CarbonInterfaceData<T : NetworkAttribute>(
    val id: String,
    val type: String,
    val attributes: T
)
package com.carbondev.carboncheck.data.remote.model.request

/**
 * Base class for all network requests to the Carbon Interface API.
 * @property type the type of request, always "electricity" for this DTO.
 */
sealed class NetworkRequest {
    abstract val type: String
}
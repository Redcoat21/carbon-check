package com.carbondev.carboncheck.data.remote.model

import kotlinx.serialization.Serializable
import com.carbondev.carboncheck.domain.model.Vendor
import com.squareup.moshi.Json
import java.util.Date

@Serializable
data class NetworkVendor(
    val id: String,
    val name: String,
    @Json(name = "created_at") val createdAt: Date,
    @Json(name = "updated_at") val updatedAt: Date,
    @Json(name = "deleted_at") val deletedAt: Date?,
) : RemoteMappable<Vendor> {
    override fun toDomainModel(): Vendor {
        return Vendor(
            id = id,
            name = name,
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt
        )
    }
}

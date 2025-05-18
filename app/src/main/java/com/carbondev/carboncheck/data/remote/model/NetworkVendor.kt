package com.carbondev.carboncheck.data.remote.model

import kotlinx.serialization.Serializable
import com.carbondev.carboncheck.domain.model.Vendor
import com.squareup.moshi.Json
import kotlinx.serialization.Contextual
import java.util.Date

@Serializable
data class NetworkVendor(
    val id: String,
    val name: String,
    @Json(name = "created_at") @Contextual val createdAt: Date,
    @Json(name = "updated_at") @Contextual val updatedAt: Date,
    @Json(name = "deleted_at") @Contextual val deletedAt: Date?,
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

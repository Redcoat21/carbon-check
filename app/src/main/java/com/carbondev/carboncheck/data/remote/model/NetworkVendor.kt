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
    companion object {
        const val TABLE_NAME = "vendors"
    }

    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
        const val DELETED_AT = "deleted_at"

        val ALL = listOf(
            ID,
            NAME,
            CREATED_AT,
            UPDATED_AT,
            DELETED_AT,
        )
    }

    override fun toDomainModel(): Vendor {
        return Vendor(
            id = id,
            name = name,
        )
    }
}

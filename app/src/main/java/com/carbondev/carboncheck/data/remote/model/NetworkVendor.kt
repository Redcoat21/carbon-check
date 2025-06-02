package com.carbondev.carboncheck.data.remote.model

import com.carbondev.carboncheck.domain.model.Vendor
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class NetworkVendor(
    val id: String,
    val name: String,
    @Json(name = "created_at") val createdAt: Date,
    @Json(name = "updated_at") val updatedAt: Date,
) : RemoteMappable<Vendor> {
    companion object {
        const val TABLE_NAME = "vendors"
    }

    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"

        val ALL = listOf(
            ID,
            NAME,
            CREATED_AT,
            UPDATED_AT,
        )
    }

    override fun toDomainModel(): Vendor {
        return Vendor(
            id = id,
            name = name,
        )
    }
}

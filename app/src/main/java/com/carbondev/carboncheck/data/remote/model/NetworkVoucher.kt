package com.carbondev.carboncheck.data.remote.model

import kotlinx.serialization.Serializable
import com.carbondev.carboncheck.domain.model.Voucher
import com.squareup.moshi.Json
import kotlinx.serialization.Contextual
import java.util.Date

@Serializable
data class NetworkVoucher(
    val id: String,
    val vendor: NetworkVendor,
    val name: String,
    val amount: Int,
    @Json(name = "created_at") @Contextual val createdAt: Date,
    @Json(name = "updated_at") @Contextual val updatedAt: Date,
    @Json(name = "deleted_at") @Contextual val deletedAt: Date?,
) : RemoteMappable<Voucher> {
    override fun toDomainModel(): Voucher {
        return Voucher(
            id = id,
            vendor = vendor.toDomainModel(),
            name = name,
            amount = amount,
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt
        )
    }
}

package com.carbondev.carboncheck.data.remote.model

import kotlinx.serialization.Serializable
import com.carbondev.carboncheck.domain.model.Voucher
import com.squareup.moshi.Json
import java.util.Date

@Serializable
data class NetworkVoucher(
    val id: String,
    val vendor: NetworkVendor,
    val name: String,
    val amount: Int,
    @Json(name = "created_at") val createdAt: Date,
    @Json(name = "updated_at") val updatedAt: Date,
    @Json(name = "deleted_at") val deletedAt: Date?,
) : RemoteMappable<Voucher> {
    override fun toDomainModel(): Voucher {

    }
}

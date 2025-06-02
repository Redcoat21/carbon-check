package com.carbondev.carboncheck.data.remote.model

import com.carbondev.carboncheck.domain.model.Voucher
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class NetworkVoucher(
    val id: String,
    val vendor: NetworkVendor,
    val name: String,
    val amount: Int,
    @Json(name = "created_at") val createdAt: Date,
    @Json(name = "updated_at") val updatedAt: Date,
) : RemoteMappable<Voucher> {
    override fun toDomainModel(): Voucher {
        return Voucher(
            id = id,
            vendor = vendor.toDomainModel(),
            name = name,
            amount = amount,
        )
    }
}

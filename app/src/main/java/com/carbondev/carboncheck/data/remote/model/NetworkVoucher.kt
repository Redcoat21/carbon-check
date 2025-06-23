package com.carbondev.carboncheck.data.remote.model

import com.carbondev.carboncheck.domain.model.Voucher
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.datetime.Instant

@JsonClass(generateAdapter = true)
data class NetworkVoucher(
    val id: String,
    val vendor: NetworkVendor,
    val name: String,
    val amount: Int,
    @Json(name = "created_at") val createdAt: Instant,
    @Json(name = "updated_at") val updatedAt: Instant,
    val points: Int
) : RemoteMappable<Voucher> {

    companion object {
        const val TABLE_NAME = "vouchers"
    }

    object Columns {
        const val ID = "id"
        const val VENDOR_ID = "vendor"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
        const val POINTS = "points"
        const val NAME = "name"
        const val AMOUNT = "amount"

        val ALL = listOf(
            ID,
            VENDOR_ID,
            CREATED_AT,
            UPDATED_AT,
            POINTS,
            NAME,
            AMOUNT
        )
    }

    override fun toDomainModel(): Voucher {
        return Voucher(
            id = id,
            vendor = vendor.toDomainModel(),
            name = name,
            amount = amount,
            points = points
        )
    }
}

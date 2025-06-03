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

    companion object {
        const val TABLE_NAME = "vouchers"
    }

    object Columns {
        const val ID = "id"
        const val VENDOR_ID = "vendor"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"

        val ALL = listOf(
            ID,
            VENDOR_ID,
            CREATED_AT,
            UPDATED_AT,
        )
    }
    override fun toDomainModel(): Voucher {
        return Voucher(
            id = id,
            vendor = vendor.toDomainModel(),
            name = name,
            amount = amount,
        )
    }
}

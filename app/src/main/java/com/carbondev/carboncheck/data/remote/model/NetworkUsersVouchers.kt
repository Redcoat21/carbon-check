package com.carbondev.carboncheck.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.datetime.Instant

@JsonClass(generateAdapter = true)
data class NetworkUsersVouchers(
    val user: NetworkUser,
    val voucher: NetworkVoucher,
    val amount: Int,
    @Json(name = "created_at") val createdAt: Instant
) {

    companion object {
        const val TABLE_NAME = "users_vouchers"
    }

    object Columns {
        const val USER = "user"
        const val VOUCHER = "voucher"
        const val AMOUNT = "amount"
        const val CREATED_AT = "created_at"

        val ALL = listOf(
            USER,
            VOUCHER,
            AMOUNT,
            CREATED_AT
        )
    }
}

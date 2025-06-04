package com.carbondev.carboncheck.domain.model

/**
 * Identifier to determine which vouchers to fetch.
 */
sealed class VoucherIdentifier {
    data class User(val userId: String): VoucherIdentifier()
    data class Vendor(val vendorId: String): VoucherIdentifier()
}

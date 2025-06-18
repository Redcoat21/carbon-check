package com.carbondev.carboncheck.domain.model

data class Voucher(
    val id: String,
    val vendor: Vendor,
    val name: String,
    val amount: Int,
)

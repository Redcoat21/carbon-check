package com.carbondev.carboncheck.domain.model

import java.util.Date

data class Voucher(
    val id: String,
    val vendor: Vendor,
    val name: String,
    val amount: Int,
    val createdAt: Date,
    val updatedAt: Date,
    val deletedAt: Date?
)

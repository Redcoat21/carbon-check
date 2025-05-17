package com.carbondev.carboncheck.domain.model

data class Voucher(
    val id: String,
    val vendor: Vendor,
    val createdAt: Date,
    val updatedAt: Date,
    val deletedAt: Date?
)

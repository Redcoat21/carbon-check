package com.carbondev.carboncheck.domain.model

data class Vendor(
    val id: String,
    val name: String,
    val createdAt: Date,
    val updatedAt: Date,
    val deletedAt: Date?
)

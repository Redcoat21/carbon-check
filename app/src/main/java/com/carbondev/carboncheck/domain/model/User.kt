package com.carbondev.carboncheck.domain.model

import java.util.Date

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val email: String,
    val createdAt: Date,
    val updatedAt: Date,
    val deletedAt: Date?
) {
    val name: String get() = "$firstName $lastName".trim()
}


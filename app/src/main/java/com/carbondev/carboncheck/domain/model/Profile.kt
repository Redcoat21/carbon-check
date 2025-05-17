package com.carbondev.carboncheck.domain.model

data class Profile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val createdAt: Date,
    val updatedAt: Date,
    val deletedAt: Date?
) {
    val name: String get() = "$firstName $lastName".trim()
}

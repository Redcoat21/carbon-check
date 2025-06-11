package com.carbondev.carboncheck.domain.model

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val email: String,
) {
    val name: String get() = "$firstName $lastName".trim()
}


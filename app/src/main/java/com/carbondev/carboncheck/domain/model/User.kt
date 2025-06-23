package com.carbondev.carboncheck.domain.model

import com.carbondev.carboncheck.data.remote.model.NetworkUser
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val email: String,
    val createdAt: Instant
) {
    val name: String get() = "$firstName $lastName".trim()

    fun toNetworkModel(): NetworkUser {
        return NetworkUser(
            id = id,
            firstName = firstName,
            lastName = lastName,
            avatarUrl = avatarUrl,
            email = email,
            createdAt = createdAt,
            updatedAt = Clock.System.now()
        )
    }
}


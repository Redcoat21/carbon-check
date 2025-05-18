package com.carbondev.carboncheck.data.remote.model

import kotlinx.serialization.Serializable
import com.carbondev.carboncheck.domain.model.Profile
import com.squareup.moshi.Json
import kotlinx.serialization.Contextual
import java.util.Date

@Serializable
data class NetworkProfile(
    val id: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "created_at") @Contextual val createdAt: Date,
    @Json(name = "updated_at") @Contextual val updatedAt: Date,
    @Json(name = "deleted_at") @Contextual val deletedAt: Date?,
    @Json(name = "avatar_url") val avatarUrl: String
) : RemoteMappable<Profile> {

    override fun toDomainModel(): Profile {
        return Profile(
            id = id,
            firstName = firstName,
            lastName = lastName,
            avatarUrl = avatarUrl,
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt
        )
    }
}
package com.carbondev.carboncheck.data.remote.model

import kotlinx.serialization.Serializable
import com.carbondev.carboncheck.domain.model.User
import com.squareup.moshi.Json
import kotlinx.serialization.Contextual
import java.util.Date

@Serializable
data class NetworkUser(
    val id: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    val email: String,
    @Json(name = "created_at") @Contextual val createdAt: Date,
    @Json(name = "updated_at") @Contextual val updatedAt: Date,
    @Json(name = "deleted_at") @Contextual val deletedAt: Date?,
    @Json(name = "avatar_url") val avatarUrl: String
) : RemoteMappable<User> {
    companion object {
        const val TABLE_NAME = "profiles"
    }

    object Columns {
        const val ID = "id"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
        const val DELETED_AT = "deleted_at"
        const val AVATAR_URL = "avatar_url"
        const val EMAIL = "email"

        val ALL = listOf(
            ID,
            FIRST_NAME,
            LAST_NAME,
            CREATED_AT,
            UPDATED_AT,
            DELETED_AT,
            AVATAR_URL,
            EMAIL
        )
    }
    override fun toDomainModel(): User {
        return User(
            id = id,
            firstName = firstName,
            lastName = lastName,
            avatarUrl = avatarUrl,
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt,
            email = email
        )
    }
}
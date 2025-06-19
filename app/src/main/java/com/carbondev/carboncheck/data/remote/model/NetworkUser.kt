package com.carbondev.carboncheck.data.remote.model

import com.carbondev.carboncheck.domain.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.datetime.Instant

@JsonClass(generateAdapter = true)
data class NetworkUser(
    val id: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    val email: String,
    @Json(name = "created_at") val createdAt: Instant,
    @Json(name = "updated_at") val updatedAt: Instant,
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
        const val AVATAR_URL = "avatar_url"
        const val EMAIL = "email"

        val ALL = arrayOf(
            ID,
            FIRST_NAME,
            LAST_NAME,
            CREATED_AT,
            UPDATED_AT,
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
            email = email
        )
    }
}
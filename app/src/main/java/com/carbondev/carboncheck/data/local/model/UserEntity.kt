package com.carbondev.carboncheck.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.carbondev.carboncheck.domain.model.User

@Entity(tableName = "user_profile")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val points: Int,
    val email: String
)

fun UserEntity.toDomainModel(): User {
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatarUrl = avatarUrl,
        points = points,
        email = email
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatarUrl = avatarUrl,
        points = points,
        email = email
    )
}
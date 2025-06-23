package com.carbondev.carboncheck.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.carbondev.carboncheck.data.local.model.UserEntity

@Dao
interface UserDao {

    @Upsert // Inserts if new, updates if exists (based on PrimaryKey)
    suspend fun saveUser(user: UserEntity)

    @Query("SELECT * FROM user_profile LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Query("DELETE FROM user_profile")
    suspend fun clearUser()
}
package com.carbondev.carboncheck.data.local.datasource

import com.carbondev.carboncheck.data.local.dao.UserDao
import com.carbondev.carboncheck.data.local.model.toEntity
import com.carbondev.carboncheck.domain.model.User
import jakarta.inject.Inject


interface UserLocalDataSource {
    suspend fun saveUser(user: User)
}

class UserLocalDataSourceImplementation @Inject constructor(
    private val userDao: UserDao
) : UserLocalDataSource {

    override suspend fun saveUser(user: User) {
        userDao.saveUser(user.toEntity())
    }
}
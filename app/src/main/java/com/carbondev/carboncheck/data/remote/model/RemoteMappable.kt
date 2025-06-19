package com.carbondev.carboncheck.data.remote.model

interface RemoteMappable<T> {
    fun toDomainModel(): T
}

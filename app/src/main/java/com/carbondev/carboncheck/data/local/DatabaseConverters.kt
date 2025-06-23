package com.carbondev.carboncheck.data.local

import androidx.room.TypeConverter
import com.carbondev.carboncheck.domain.common.ActivityType
import com.carbondev.carboncheck.domain.model.CarbonData
import kotlinx.datetime.Instant

/**
 * Type converters to allow Room to reference complex data types.
 */
class DatabaseConverters {

    /**
     * Converts a Long timestamp (milliseconds since epoch) into a kotlinx.datetime.Instant.
     * Room will use this when reading from the database.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return value?.let { Instant.fromEpochMilliseconds(it) }
    }

    /**
     * Converts a kotlinx.datetime.Instant into a Long timestamp for storing in the database.
     * Room will use this when writing to the database.
     */
    @TypeConverter
    fun dateToTimestamp(date: Instant?): Long? {
        return date?.toEpochMilliseconds()
    }

    /**
     * Converts a Double (representing grams) into a CarbonData domain model.
     * Room will use this when reading from the database.
     */
    @TypeConverter
    fun fromGram(value: Double?): CarbonData? {
        return value?.let { CarbonData(gram = it) }
    }

    /**
     * Converts a CarbonData object into a Long (its 'gram' value) for storing in the database.
     * Room will use this when writing to the database.
     */
    @TypeConverter
    fun carbonToGram(carbon: CarbonData?): Double? {
        return carbon?.gram
    }

    /**
     * Converts a String from the database into an ActivityType enum.
     * Room will use this when reading from the database.
     */
    @TypeConverter
    fun toActivityType(value: String?) = value?.let { enumValueOf<ActivityType>(it) }

    /**
     * Converts an ActivityType enum into a String for storing in the database.
     * Room will use this when writing to the database.
     */
    @TypeConverter
    fun fromActivityType(value: ActivityType?) = value?.name
}

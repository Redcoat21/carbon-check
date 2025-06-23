package com.carbondev.carboncheck.data.local.entity

import androidx.room.*
import com.carbondev.carboncheck.domain.common.ActivityType
import com.carbondev.carboncheck.domain.model.Activity
import com.carbondev.carboncheck.domain.model.CarbonData
import kotlinx.datetime.Instant

/**
 * Represents a single row in the local 'activities' database table.
 * It's designed for Room persistence and can be mapped to and from the domain model.
 */
@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey
    @ColumnInfo(name = "activity_id")
    val activityId: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "type")
    val type: ActivityType,

    @ColumnInfo(name = "datetime")
    val datetime: Instant,

    @ColumnInfo(name = "carbon")
    val carbonInGrams: Int, // Store the base unit (grams) for simplicity

    @ColumnInfo(name = "flight_destination")
    val flightDestination: String?,

    @ColumnInfo(name = "flight_departure")
    val flightDeparture: String?,

    @ColumnInfo(name = "people")
    val people: Int?,

    @ColumnInfo(name = "distance")
    val distance: Int?,

    @ColumnInfo(name = "food")
    val foodType: String?,

    @ColumnInfo(name = "weight")
    val weightInGrams: Int?,

    @ColumnInfo(name = "vehicle")
    val vehicleType: String?
) {
    /**
     * Converts this database entity into a clean domain model.
     */
    fun toDomainModel(): Activity {
        val carbonGrams = this.carbonInGrams.toDouble()
        return Activity(
            id = this.activityId,
            userId = this.userId,
            type = this.type,
            datetime = this.datetime,
            carbon = CarbonData(
                gram = carbonGrams,
                kilogram = carbonGrams / 1000.0,
                pound = carbonGrams * 0.00220462,
                metricTon = carbonGrams / 1_000_000.0
            ),
            flightDestination = this.flightDestination,
            flightDeparture = this.flightDeparture,
            people = this.people,
            distance = this.distance,
            foodType = this.foodType,
            weightInGrams = this.weightInGrams,
            vehicleType = this.vehicleType
        )
    }

    companion object {
        /**
         * Creates a database entity from a clean domain model.
         */
        fun fromDomainModel(activity: Activity): ActivityEntity {
            return ActivityEntity(
                activityId = activity.id,
                userId = activity.userId,
                type = activity.type,
                datetime = activity.datetime,
                carbonInGrams = activity.carbon.gram.toInt(), // Extract base unit for storage
                flightDestination = activity.flightDestination,
                flightDeparture = activity.flightDeparture,
                people = activity.people,
                distance = activity.distance,
                foodType = activity.foodType,
                weightInGrams = activity.weightInGrams,
                vehicleType = activity.vehicleType
            )
        }
    }
}

/**
 * Room TypeConverter to store and retrieve kotlinx.datetime.Instant objects.
 * They are stored as Long (epoch milliseconds) in the database.
 * NOTE: Your Room Database class must be annotated with @TypeConverters(Converters::class)
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return value?.let { Instant.fromEpochMilliseconds(it) }
    }

    @TypeConverter
    fun instantToTimestamp(instant: Instant?): Long? {
        return instant?.toEpochMilliseconds()
    }
}
package com.carbondev.carboncheck.domain.model

import com.carbondev.carboncheck.domain.common.ActivityType
import kotlinx.datetime.Instant

/**
 * Represents a single carbon-emitting activity in the domain layer.
 * This model is UI-agnostic and contains all necessary raw data.
 */
data class Activity(
    val id: String,
    val userId: String,
    val type: ActivityType, // e.g., "FLIGHT", "FOOD", "TRANSPORT"
    val datetime: Instant,
    val carbon: CarbonData,

    // Optional fields for activity-specific details
    val flightDestination: String?,
    val flightDeparture: String?,
    val people: Int?,
    val distance: Int?,
    val foodType: String?,
    val weightInGrams: Int?,
    val vehicleType: String?
)
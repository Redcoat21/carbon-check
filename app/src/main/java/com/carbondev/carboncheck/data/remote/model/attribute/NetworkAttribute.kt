package com.carbondev.carboncheck.data.remote.model.attribute

import kotlinx.datetime.Instant

/**
 * Base class for carbon interface DTO attribute responses.
 * @property estimatedAt the timestamp when the data was estimated, in ISO 8601 format in UTC Time format as an [Instant].
 * @property carbonG the carbon emissions in grams.
 * @property carbonLb the carbon emissions in pounds.
 * @property carbonKg the carbon emissions in kilograms.
 * @property carbonMt the carbon emissions in metric tons.
 */
sealed class NetworkAttribute {
    abstract val estimatedAt: Instant
    abstract val carbonG: Double
    abstract val carbonLb: Double
    abstract val carbonKg: Double
    abstract val carbonMt: Double
}
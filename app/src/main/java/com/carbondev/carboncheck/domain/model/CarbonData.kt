package com.carbondev.carboncheck.domain.model

data class CarbonData(
    val gram: Double = 0.0,
    val kilogram: Double = 0.0,
    val pound: Double = 0.0,
    val metricTon: Double = 0.0,
) {
    operator fun plus(other: CarbonData): CarbonData {
        return CarbonData(
            gram = this.gram + other.gram,
            kilogram = this.kilogram + other.kilogram,
            pound = this.pound + other.pound,
            metricTon = this.metricTon + other.metricTon
        )
    }
}
package com.carbondev.carboncheck.domain.model

data class CarbonData(
    val gram: Double = 0.0,
    val kilogram: Double = 0.0,
    val pound: Double = 0.0,
    val metricTon: Double = 0.0,
) {
    companion object {
        private const val GRAMS_IN_KILOGRAM = 1000.0
        private const val GRAMS_IN_POUND = 453.59237
        private const val GRAMS_IN_METRIC_TON = 1_000_000.0

        fun fromGrams(gram: Double): CarbonData {
            return CarbonData(
                gram = gram,
                kilogram = gram / GRAMS_IN_KILOGRAM,
                pound = gram / GRAMS_IN_POUND,
                metricTon = gram / GRAMS_IN_METRIC_TON
            )
        }

        fun fromKilograms(kg: Double): CarbonData {
            val grams = kg * GRAMS_IN_KILOGRAM
            return fromGrams(grams)
        }

        fun fromPounds(lb: Double): CarbonData {
            val grams = lb * GRAMS_IN_POUND
            return fromGrams(grams)
        }

        fun fromMetricTons(mt: Double): CarbonData {
            val grams = mt * GRAMS_IN_METRIC_TON
            return fromGrams(grams)
        }
    }

    operator fun plus(other: CarbonData): CarbonData {
        return fromGrams(this.gram + other.gram)
    }

}
package com.carbondev.carboncheck.domain.model

import androidx.compose.ui.graphics.Color
import co.yml.charts.ui.piechart.models.PieChartData

data class CarbonStats(
    val transport: CarbonData = CarbonData(),
    val flight: CarbonData = CarbonData(),
    val food: CarbonData = CarbonData()
)

fun CarbonStats.toPieSlices(): List<PieChartData.Slice> =
    listOf(
        PieChartData.Slice("Transport", transport.kilogram.toFloat(), Color(0xFF66BB6A)),
        PieChartData.Slice("Flight", flight.kilogram.toFloat(), Color(0xFFEF5350)),
        PieChartData.Slice("Food", food.kilogram.toFloat(), Color(0xFF42A5F5))
    )
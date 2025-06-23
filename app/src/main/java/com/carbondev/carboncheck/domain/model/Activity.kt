package com.carbondev.carboncheck.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Activity(
    val icon: ImageVector,
    val title: String,
    val time: String,
    val carbon: CarbonData
)
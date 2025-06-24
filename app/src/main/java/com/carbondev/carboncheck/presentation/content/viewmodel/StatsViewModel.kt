package com.carbondev.carboncheck.presentation.content.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carbondev.carboncheck.domain.model.CarbonStats
import com.carbondev.carboncheck.domain.usecase.activity.GetMonthlyCarbonStatsUseCase
import com.carbondev.carboncheck.domain.usecase.activity.GetWeeklyCarbonStatsUseCase
import com.carbondev.carboncheck.domain.usecase.activity.GetYearlyCarbonStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    weekly: GetWeeklyCarbonStatsUseCase,
    monthly: GetMonthlyCarbonStatsUseCase,
    yearly: GetYearlyCarbonStatsUseCase
) : ViewModel() {

    val weeklyStats = weekly()
        .stateIn(viewModelScope, SharingStarted.Lazily, CarbonStats())

    val monthlyStats = monthly()
        .stateIn(viewModelScope, SharingStarted.Lazily, CarbonStats())

    val yearlyStats = yearly()
        .stateIn(viewModelScope, SharingStarted.Lazily, CarbonStats())
}
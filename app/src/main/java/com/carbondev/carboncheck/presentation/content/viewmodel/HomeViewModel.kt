package com.carbondev.carboncheck.presentation.content.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.carbondev.carboncheck.domain.model.Activity
import com.carbondev.carboncheck.domain.model.CarbonData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

// The UI state now holds a CarbonData object for the daily total.
data class HomeUiState(
    val userName: String = "",
    val todaysCo2: CarbonData = CarbonData(),
    val dailyTarget: Float = 5.6f, // Target remains a float in kg
    val recentActivities: List<Activity> = emptyList(),
    val isLoading: Boolean = true
)


@HiltViewModel
class HomeViewModel @Inject constructor(
    // Inject your use cases here, e.g.,
    // private val getUserProfileUseCase: GetUserProfileUseCase,
    // private val getCarbonHistoryUseCase: GetCarbonHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // In a real app, you would launch a coroutine to fetch data from your repository
        loadData()
    }

    private fun loadData() {
        // This is where you would call your use cases to get real data.
        _uiState.update {
            val sampleActivities = getSampleActivities()
            // Calculate the initial total CO2 by summing up the CarbonData of each activity.
            val totalCo2 = sampleActivities.fold(CarbonData()) { acc, activity ->
                acc + activity.carbon
            }
            it.copy(
                userName = "Vivek", // Fetched from user profile
                todaysCo2 = totalCo2,
                recentActivities = sampleActivities,
                isLoading = false
            )
        }
    }

    // Helper function to create a fully populated CarbonData object from a single kilogram value.
    private fun createCarbonDataFromKg(kg: Double): CarbonData {
        return CarbonData(
            gram = kg * 1000,
            kilogram = kg,
            pound = kg * 2.20462,
            metricTon = kg / 1000
        )
    }

    private fun getSampleActivities(): List<Activity> {
        // This function simulates fetching recent activities from a repository.
        // It now uses the helper function to create CarbonData.
        return listOf(
            Activity(Icons.Default.Home, "Flight to Bali", "6:00 am • 2 hours", createCarbonDataFromKg(150.0)),
            Activity(Icons.Default.Home, "Morning commute", "8:30 am • 25 minutes", createCarbonDataFromKg(6.1)),
            Activity(Icons.Default.Home, "Lunch (Steak)", "12:15 pm • 45 minutes", createCarbonDataFromKg(7.5)),
            Activity(Icons.Default.Home, "Bike ride home", "5:00 pm • 30 minutes", createCarbonDataFromKg(0.1)),
            Activity(Icons.Default.Home, "Evening walk", "7:30 pm • 15 minutes", createCarbonDataFromKg(0.0))
        )
    }

    // This function is now more robust for adding a new activity.
    fun addActivity(title: String, time: String, icon: ImageVector, co2InKg: Double) {
        val newActivity = Activity(
            icon = icon,
            title = title,
            time = time,
            carbon = createCarbonDataFromKg(co2InKg)
        )

        _uiState.update { currentState ->
            val newActivities = listOf(newActivity) + currentState.recentActivities
            val newTotalCo2 = currentState.todaysCo2 + newActivity.carbon

            currentState.copy(
                recentActivities = newActivities,
                todaysCo2 = newTotalCo2
            )
        }
    }
}

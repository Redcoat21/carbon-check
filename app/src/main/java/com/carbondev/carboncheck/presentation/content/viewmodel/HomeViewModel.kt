package com.carbondev.carboncheck.presentation.content.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewModelScope
import com.carbondev.carboncheck.domain.common.ErrorType // Using your provided ErrorType
import com.carbondev.carboncheck.domain.common.Result // Using your provided Result
import com.carbondev.carboncheck.domain.model.Activity
import com.carbondev.carboncheck.domain.model.CarbonData
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Flight
//import com.carbondev.carboncheck.domain.usecase.activity.GetRecentActivitiesUseCase
import com.carbondev.carboncheck.domain.usecase.user.GetCurrentUserUseCase
import com.carbondev.carboncheck.presentation.common.UiState
import com.carbondev.carboncheck.presentation.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// HomeUiState remains the same
data class HomeUiState(
    val userName: String = "",
    val todaysCo2: CarbonData = CarbonData(),
    val dailyTarget: Float = 5.6f,
    val recentActivities: List<Activity> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
//    private val getRecentActivitiesUseCase: GetRecentActivitiesUseCase
) : BaseViewModel() {

    init {
        loadData()
    }

    fun loadData() = viewModelScope.launch {
        setLoading()

        // Step 1: Fetch the current user
        val userResult = getCurrentUserUseCase()

        if (userResult is Result.Error) {
            // Updated 'when' block to use your specific ErrorType enum
            val errorMessage = when (userResult.type) {
                ErrorType.NOT_FOUND_ERROR -> "Your profile could not be found. Please try logging in again."
                ErrorType.NETWORK_ERROR -> "Please check your internet connection and try again."
                else -> userResult.message ?: "An unexpected error occurred while loading your profile."
            }
            setError(errorMessage)
            return@launch
        }
        
        val user = (userResult as Result.Success).data

        // Step 2: Fetch recent activities
//        val activitiesResult = getRecentActivitiesUseCase(user.id)
//
//        if (activitiesResult is Result.Error) {
//            // Updated 'when' block for the second API call
//            val errorMessage = when (activitiesResult.type) {
//                ErrorType.NETWORK_ERROR -> "Could not fetch recent activities. Please check your connection."
//                ErrorType.DATABASE_ERROR -> "There was an issue retrieving your data."
//                else -> activitiesResult.message ?: "An unexpected error occurred."
//            }
//            setError(errorMessage)
//            return@launch
//        }
//
//        // --- Happy Path ---
//        val activities = activitiesResult.data
//        val totalCo2 = activities.fold(CarbonData()) { acc, activity ->
//            acc + activity.carbon
//        }

        val sampleActivities = getSampleActivities()
        val totalCo2 = sampleActivities.fold(CarbonData()) { acc, activity ->
            acc + activity.carbon
        }

        setSuccess(
            HomeUiState(
                userName = user.name,
                todaysCo2 = totalCo2,
                recentActivities = sampleActivities
            )
        )
    }

    private fun getSampleActivities(): List<Activity> {
        // This function simulates fetching recent activities from a repository.
        // It now uses the helper function to create CarbonData.
        return listOf(
            Activity(Icons.Default.Flight, "Flight to Bali", "6:00 am • 2 hours", createCarbonDataFromKg(150.0)),
            Activity(Icons.Default.DirectionsCar, "Travel to Work", "8:30 am • 25 minutes", createCarbonDataFromKg(6.1)),
            Activity(Icons.Default.Fastfood, "Lunch (Steak)", "12:15 pm • 45 minutes", createCarbonDataFromKg(7.5)),
        )
    }


    // `addActivity` and helper functions remain unchanged
    fun addActivity(title: String, time: String, icon: ImageVector, co2InKg: Double) {
        val currentState = uiState.value
        if (currentState is UiState.Success<*>) {
            val currentData = currentState.data as? HomeUiState ?: return
            val newActivity = Activity(
                icon = icon,
                title = title,
                time = time,
                carbon = createCarbonDataFromKg(co2InKg)
            )
            val newState = currentData.copy(
                recentActivities = listOf(newActivity) + currentData.recentActivities,
                todaysCo2 = currentData.todaysCo2 + newActivity.carbon
            )
            setSuccess(newState)
        }
    }

    private fun createCarbonDataFromKg(kg: Double): CarbonData {
        return CarbonData(
            gram = kg * 1000,
            kilogram = kg,
            pound = kg * 2.20462,
            metricTon = kg / 1000
        )
    }
}
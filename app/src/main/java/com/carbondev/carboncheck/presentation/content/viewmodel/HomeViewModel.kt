package com.carbondev.carboncheck.presentation.content.viewmodel

import androidx.lifecycle.viewModelScope
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.model.Activity
import com.carbondev.carboncheck.domain.model.CarbonData
import com.carbondev.carboncheck.domain.repository.ActivityRepository
import com.carbondev.carboncheck.domain.usecase.activity.GetActivitiesUseCase
import com.carbondev.carboncheck.domain.usecase.activity.GetTodaysCarbonTotalUseCase
import com.carbondev.carboncheck.domain.usecase.user.GetCurrentUserUseCase
import com.carbondev.carboncheck.presentation.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val userName: String = "",
    val todaysCo2: CarbonData = CarbonData(),
    val dailyTarget: Float = 5.6f,
    val recentActivities: List<Activity> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase,
    private val getTodaysCarbonTotalUseCase: GetTodaysCarbonTotalUseCase,
    private val activityRepository: ActivityRepository
) : BaseViewModel() {

    init {
        // 1. Initial refresh of data from the remote source.
        refreshData()

        // 2. Start observing all data streams and combine them into a single UI state.
        observeDataStreams()
    }

    private fun refreshData() = viewModelScope.launch {
        setLoading()
        // This fetches from remote and updates the local cache,
        // which will then automatically update our observing flows.
        activityRepository.refreshActivities()
    }

    private fun observeDataStreams() {
        viewModelScope.launch {
            // First, get the user. This is a one-time operation.
            val userResult = getCurrentUserUseCase()
            if (userResult is Result.Error) {
                setError(userResult.message ?: "Could not load user profile.")
                return@launch
            }
            val user = (userResult as Result.Success).data

            // Now, combine the flows that will update over time.
            val activitiesFlow = getActivitiesUseCase()
            val todaysCarbonTotalFlow = getTodaysCarbonTotalUseCase()

            combine(activitiesFlow, todaysCarbonTotalFlow) { activities, todaysTotal ->
                // This block will be re-executed whenever activities or their total changes.
                HomeUiState(
                    userName = (if (user.name == "") { "Guest" } else { user.name }).toString(),
                    todaysCo2 = todaysTotal,
                    recentActivities = activities
                )
            }.collect { combinedState ->
                // Emit the new, combined state as a success.
                setSuccess(combinedState)
            }
        }
    }
}
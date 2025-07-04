package com.carbondev.carboncheck.domain.usecase.activity

import com.carbondev.carboncheck.domain.common.ActivityType
import com.carbondev.carboncheck.domain.model.CarbonData
import com.carbondev.carboncheck.domain.model.CarbonStats
import com.carbondev.carboncheck.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class GetWeeklyCarbonStatsUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    operator fun invoke(): Flow<CarbonStats> =
        repository.getActivities().map { list ->
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            val weekAgo = now.minus(DatePeriod(days = 7))
            val weekly = list.filter { it.datetime.toLocalDateTime(TimeZone.currentSystemDefault()).date >= weekAgo }

            fun sumBy(type: ActivityType) =
                weekly.filter { it.type == type }
                    .fold(CarbonData()) { acc, a -> acc + a.carbon }

            CarbonStats(
                transport = sumBy(ActivityType.TRANSPORT),
                flight    = sumBy(ActivityType.FLIGHT),
                food      = sumBy(ActivityType.FOOD)
            )
        }
}

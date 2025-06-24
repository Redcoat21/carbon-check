package com.carbondev.carboncheck.presentation.content.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.carbondev.carboncheck.domain.model.toPieSlices
import com.carbondev.carboncheck.presentation.content.viewmodel.StatsViewModel

@Composable
fun StatsPage(
    modifier: Modifier = Modifier,
    viewModel: StatsViewModel = hiltViewModel()
) {
    val weekly by viewModel.weeklyStats.collectAsState()
    val monthly by viewModel.monthlyStats.collectAsState()
    val yearly by viewModel.yearlyStats.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { OurPieChart("weekly", weekly.toPieSlices()) }
        item { OurPieChart("monthly", monthly.toPieSlices()) }
        item { OurPieChart("yearly", yearly.toPieSlices()) }
        item { Spacer(Modifier.height(100.dp)) }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OurPieChart(
    title: String,
    slices: List<PieChartData.Slice>
) {
    val pieData = PieChartData(
        slices = slices,
        plotType = PlotType.Donut,
    )

    Spacer(Modifier.height(16.dp))
    Text("Your ${title} Comparison", style = MaterialTheme.typography.titleMedium)
    Spacer(Modifier.height(12.dp))
    DonutPieChart(
        modifier = Modifier.size(400.dp),
        pieChartData = pieData,
        pieChartConfig = PieChartConfig(
            showSliceLabels = false,
            labelVisible = true,
            labelType = PieChartConfig.LabelType.VALUE,
            labelColor = MaterialTheme.colorScheme.primary,
            isAnimationEnable = true,
            animationDuration = 800,
            isSumVisible = true,
            sumUnit = "Kg",
            labelFontSize = 50.sp,
            backgroundColor = MaterialTheme.colorScheme.background
        )
    )

    Spacer(Modifier.height(16.dp))
    LegendRow(slices)
}

@Composable
fun LegendRow(slices: List<PieChartData.Slice>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {
        slices.forEach { slice ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(slice.color)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = slice.label, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SinglePieStatsPreview() {
    MaterialTheme {
        StatsPage()
    }
}
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData

@Composable
fun StatsPage(
    modifier: Modifier = Modifier,
    totalCarbon: Int = 100,
) {
    val scrollState = rememberScrollState()

    val weeklySlices = listOf(
        PieChartData.Slice("Transport", 40f, Color(0xFF66BB6A)),
        PieChartData.Slice("Energy", 50f, Color(0xFFEF5350)),
        PieChartData.Slice("Waste", 20f, Color(0xFF42A5F5))
    )

    val monthlySlices = listOf(
        PieChartData.Slice("Transport", 150.2f, Color(0xFF66BB6A)),
        PieChartData.Slice("Energy", 120f, Color(0xFFEF5350)),
        PieChartData.Slice("Waste", 54f, Color(0xFF42A5F5))
    )

    val yearlySlices = listOf(
        PieChartData.Slice("Transport", 314.8f, Color(0xFF66BB6A)),
        PieChartData.Slice("Energy", 302.1f, Color(0xFFEF5350)),
        PieChartData.Slice("Waste", 117.5f, Color(0xFF42A5F5))
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            OurPieChart("weekly", weeklySlices)
        }
        item {
            OurPieChart("monthly", monthlySlices)
        }
        item {
            OurPieChart("yearly", yearlySlices)
        }
        item {
            Spacer(Modifier.height(100.dp))
        }
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
fun Legend(slices: List<PieChartData.Slice>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        slices.forEach { slice ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(slice.color)
                )
                Spacer(Modifier.width(8.dp))
                Text(text = slice.label, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
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
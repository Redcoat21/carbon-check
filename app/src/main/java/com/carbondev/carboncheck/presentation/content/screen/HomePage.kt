package com.carbondev.carboncheck.presentation.content.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.carbondev.carboncheck.R
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.carbondev.carboncheck.presentation.Routes
import com.carbondev.carboncheck.domain.model.Activity
import com.carbondev.carboncheck.domain.model.CarbonData
import com.carbondev.carboncheck.presentation.content.viewmodel.HomeUiState
import com.carbondev.carboncheck.presentation.content.viewmodel.HomeViewModel

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
)   {
    // just a dummy
    val sampleActivities = listOf(
        Activity(Icons.Default.Home, "Car ride", "8:00 pm • 20 minutes", 6f),
        Activity(Icons.Default.Home, "Walk", "5:30 pm • 15 minutes", 0f),
        Activity(Icons.Default.Home, "Car ride", "12:00 pm • 10 minutes", 4f),
        Activity(Icons.Default.Home, "Bike ride", "9:00 am • 30 minutes", 1.2f),
        Activity(Icons.Default.Home, "Flight", "6:00 am • 2 hours", 150f)
    )

    // Collect the entire UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // A Box to handle the loading state
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            // Main content column
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Welcoming(userName = uiState.userName)

                TodayImpactCard(
                    // Pass the entire CarbonData object
                    carbonData = uiState.todaysCo2,
                    dailyTarget = uiState.dailyTarget,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

//        WeeklyProgressHeader(modifier = Modifier.weight(1f))
        RecentActivitiesList(
            activities = uiState.recentActivities,
            onAddClick = { navController.navigate(Routes.Add.route) }
            )
        }
    }
}

@Composable
fun TodayImpactCard(
    // Updated parameter to accept the CarbonData class
    carbonData: CarbonData,
    dailyTarget: Float,
    modifier: Modifier = Modifier
) {
    // Calculate progress using the kilogram value
    val progress = if (dailyTarget > 0) (carbonData.kilogram.toFloat() / dailyTarget).coerceIn(0f, 1f) else 0f

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Today's Impact", style = MaterialTheme.typography.titleMedium)
            Text(
                // Display the kilogram value from the CarbonData object
                text = "%.1f kg".format(carbonData.kilogram),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text("CO₂ emissions", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Text(
                text = "${((1 - progress) * 100).toInt()}% below daily target",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }
    }
}

@Composable
fun Welcoming(
    modifier: Modifier = Modifier,
    userName: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Hi $userName,",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Track Your Carbon Footprint!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Image(
                painter = painterResource(id = R.drawable.eco),
                contentDescription = "App Logo",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Inside
            )
        }
    }
}

// NOTE: The Activity data class is now imported from the ViewModel file.
// The local definition has been removed.

@Composable
fun ActivityRow(item: Activity, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(item.icon, contentDescription = item.title, modifier = Modifier.size(32.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
            Text(text = item.time, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        // Display the kilogram value from the item's CarbonData
        Text(text = "%.1f kg CO₂".format(item.carbon.kilogram), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun RecentActivitiesList(
    activities: List<Activity>,
    onAddClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recent activities", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)
                IconButton(onClick = onAddClick) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add Activity",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        items(activities) { activity ->
            ActivityRow(activity)
        }
    }
}

//================ PREVIEWS ================

// Helper function to create CarbonData for previews
private fun createPreviewCarbonData(kg: Double) = CarbonData(kg * 1000, kg, kg * 2.20462, kg / 1000)

// Updated preview state to use the new data structures
val previewState = HomeUiState(
    userName = "Vivek",
    todaysCo2 = createPreviewCarbonData(4.2),
    dailyTarget = 5.6f,
    recentActivities = listOf(
        Activity(Icons.Default.Home, "Car ride", "8:00 pm • 20 minutes", createPreviewCarbonData(6.0)),
        Activity(Icons.Default.Home, "Walk", "5:30 pm • 15 minutes", createPreviewCarbonData(0.0)),
        Activity(Icons.Default.Home, "Car ride", "12:00 pm • 10 minutes", createPreviewCarbonData(4.0))
    ),
    isLoading = false
)

@Preview(showBackground = true, name = "HomePage Preview Light")
@Composable
fun HomePagePreviewLight() {
    MaterialTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Welcoming(userName = previewState.userName)
                TodayImpactCard(
                    carbonData = previewState.todaysCo2,
                    dailyTarget = previewState.dailyTarget,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                RecentActivitiesList(activities = previewState.recentActivities)
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "HomePage Preview Dark")
@Composable
fun HomePagePreviewDark() {
    MaterialTheme(colorScheme = darkColorScheme()) { // Assuming you have a darkColorScheme
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Welcoming(userName = previewState.userName)
                TodayImpactCard(
                    carbonData = previewState.todaysCo2,
                    dailyTarget = previewState.dailyTarget,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                RecentActivitiesList(activities = previewState.recentActivities)
            }
        }
    }
}
